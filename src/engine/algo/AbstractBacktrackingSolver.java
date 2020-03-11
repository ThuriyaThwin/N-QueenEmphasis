package engine.algo;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspSolver;
import engine.csp.Variable;
import engine.csp.inference.InferenceLog;
import nqueens.view.NQueensBoard;
import util.Tasks;
import util.Timer;

import java.util.Optional;

/**
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.5, Page 215.<br>
 * <br>
 * <p>
 * <pre>
 * <code>
 * function BACKTRACKING-SEARCH(engine.csp) returns a solution, or failure
 *    return BACKTRACK({ }, engine.csp)
 *
 * function BACKTRACK(assignment, engine.csp) returns a solution, or failure
 *    if assignment is complete then return assignment
 *    var = SELECT-UNASSIGNED-VARIABLE(engine.csp)
 *    for each value in ORDER-DOMAIN-VALUES(var, assignment, engine.csp) do
 *       if value is consistent with assignment then
 *          add {var = value} to assignment
 *          inferences = INFERENCE(engine.csp, var, value)
 *          if inferences != failure then
 *             add inferences to assignment
 *             result = BACKTRACK(assignment, engine.csp)
 *             if result != failure then
 *                return result
 *          remove {var = value} and inferences from assignment
 *    return failure
 * </code>
 * </pre>
 * <p>
 * Figure 6.5 A simple backtracking algorithm for constraint satisfaction
 * problems. The algorithm is modeled on the recursive depth-first search of
 * Chapter 3. By varying the functions SELECT-UNASSIGNED-VARIABLE and
 * ORDER-DOMAIN-VALUES, we can implement the general-purpose heuristic discussed
 * in the text. The function INFERENCE can optionally be used to impose arc-,
 * path-, or k-consistency, as desired. If a value choice leads to failure
 * (noticed wither by INFERENCE or by BACKTRACK), then value assignments
 * (including those made by INFERENCE) are removed from the current assignment
 * and a new value is tried.
 *
 * @param <VAR> Type which is used to represent variables
 * @param <VAL> Type which is used to represent the values in the domains
 */
public abstract class AbstractBacktrackingSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {
    static int count = 0;
    static double time = 0;
    boolean solveAll = false;
    private int numberOfBacktrack = 0;
    private int test = 0;
    private int numberOfNodesVisited = 0;
    private int numberOfNodesAssigned = 0;

    /**
     * solve the CSP with naive.
     */

    public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
        Timer.tic();
        Assignment<VAR, VAL> result = backtrack(csp, new Assignment<>());
        time = Timer.toc();
        this.solveAll = false;
        return result != null ? Optional.of(result) : Optional.empty();
    }

    /**
     * solve the CSP with Specific Assignment.
     */
    public Optional<Assignment<VAR, VAL>> solveSpecific(CSP<VAR, VAL> csp, Assignment initial) {
        /*if(test==0 ) {// For desired user input
               //    var = Util.selectRandomlyFromList(csp.getVariables());//console random
                 var=csp.getVariables().get(1);
                  test++;
              }*/
        Timer.tic();
        Assignment<VAR, VAL> result = backtrack(csp, initial);
        time = Timer.toc();
        this.solveAll = false;
        return result != null ? Optional.of(result) : Optional.empty();
    }

    /**
     * solve the CSP for all solutions.
     */
    public Optional<Assignment<VAR, VAL>> solveAll(CSP<VAR, VAL> csp) {
        this.clearAll();
        this.solveAll = true;
        return this.solve(csp);
    }

    // function BACKTRACK(assignment, csp) returns a solution, or failure
    private Assignment<VAR, VAL> backtrack(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        Assignment<VAR, VAL> result = null;
        // if assignment is complete then return assignment
        if (assignment.isComplete(csp.getVariables()) || Tasks.currIsCancelled()) {
            if (solveAll) {
                // show a joption pane dialog using showMessageDialog
                ++count;
                //JOptionPane.showMessageDialog(new JFrame("Solution"), ++count + " Solution Found :" + assignment.toString());
            } else {
                result = assignment;
            }
        } else {
            // var <- SELECT-UNASSIGNED-VARIABLE(assignment, csp)
            VAR var = selectUnassignedVariable(csp, assignment);
             if(test==0 ) {// For desired user input
                 var=csp.getVariables().get(NQueensBoard.y);
                  test++;
              }
            // for each value in ORDER-DOMAIN-VALUES(var, assignment, csp) do
            for (VAL value : orderDomainValues(csp, assignment, var)) {
                // if value is consistent with assignment then
                assignment.add(var, value);
                //add {var = value} to assignment
                numberOfNodesVisited++;
                fireStateChanged(csp, assignment, var);
                if (assignment.isConsistent(csp.getConstraints(var))) {
                    numberOfNodesAssigned++;
                    // inferences <- INFERENCE(csp, assignment, var, value)
                    InferenceLog<VAR, VAL> log = inference(csp, assignment, var);
                    // if inferences != failure then
                    if (!log.isEmpty())
                        fireStateChanged(csp, new Assignment<>(), var);
                    if (!log.inconsistencyFound()) {
                        result = backtrack(csp, assignment);//even result==null the code not reach numberOfBacktrack until it returns result=null;
                        // if result != failure then
                        if (result != null)
                            // return result
                            break;
                        if (result == null) {
                            numberOfBacktrack++;
                        }
                    }
                    // remove inferences from assignment
                    log.undo(csp);
                }
                // remove {var = value} from assignment
                assignment.remove(var);
            }
            //It is a place which occurs deadend variable.
        }
        // return failure
        return result;//result==null when variable has no value to try.
    }

    /**
     * Primitive operation, selecting a not yet assigned variable.
     */
    protected abstract VAR selectUnassignedVariable(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment);

    /**
     * Primitive operation, ordering the engine.csp.domain values of the specified variable.
     */
    protected abstract Iterable<VAL> orderDomainValues(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);

    /**
     * Primitive operation, which tries to optimize the CSP representation with respect to a new assignment.
     *
     * @param var The variable which just got a new value in the assignment.
     * @return An object which provides information about
     * (1) whether changes have been performed,
     * (2) possibly inferred empty domains, and
     * (3) how to restore the original CSP.
     */

    protected abstract InferenceLog<VAR, VAL> inference(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);


    public int getNumberOfNodesVisited() {
        return numberOfNodesVisited + 1;
    }

    public double getTime() {
        return time;
    }

    public int getNumberOfSolution() {
        return count;
    }

    public void clearAll() {
        this.numberOfNodesAssigned = 0;
        this.numberOfNodesVisited = 0;
        this.numberOfBacktrack = 0;
        test = 0;
        this.time = 0.0;
        this.count = 0;
    }
}
