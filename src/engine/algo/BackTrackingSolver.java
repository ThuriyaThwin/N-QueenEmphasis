package engine.algo;


import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspSolver;
import engine.csp.Variable;
import engine.csp.inference.InferenceLog;
import util.Tasks;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
 *
 */
public class BackTrackingSolver<VAR extends Variable, VAL>  extends CspSolver<VAR, VAL>{
    private int numberOfBacktrack = 0;
    private int numberOfNodesVisited=0;
    private int numberOfNodesAssigned=0;
    static int count = 0;
    boolean solveAll=false;

    /** Applies a recursive backtracking search to solve the CSP. */
    public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
        Assignment<VAR, VAL> result = backtrack(csp, new Assignment<>());
        this.solveAll=false;
        return result != null ? Optional.of(result) : Optional.empty();
    }

    public Optional<Assignment<VAR, VAL>> solveAll(CSP<VAR,VAL> csp)
    {
        this.clearAll();
        this.solveAll=true;
        return this.solve(csp);
    }

    private Assignment<VAR, VAL> backtrack(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        Assignment<VAR, VAL> result = null;
        if (assignment.isComplete(csp.getVariables()) || Tasks.currIsCancelled()) {
            if(solveAll) {
                // show a joption pane dialog using showMessageDialog
                JOptionPane.showMessageDialog(new JFrame("Solution"), ++count + " Solution Found :" + assignment.toString());
                count++;
            }else {
                result = assignment;
            }
        } else {
            VAR var = VARIABLE(csp, assignment);
            for (VAL value : VALUE(csp, assignment, var)) {
                assignment.add(var, value);
                numberOfNodesVisited++;
                fireStateChanged(csp, assignment, var);
                if (assignment.isConsistent(csp.getConstraints(var))) {
                    numberOfNodesAssigned++;
                        result = backtrack(csp, assignment);
                        if (result != null)
                            break;
                        if(result==null) {
                            numberOfBacktrack++;
                        }
                    }
                assignment.remove(var);
                }
            }

        return result;
    }

    /**
     * Primitive operation, selecting a not yet assigned variable.
     */



    public int getNumberOfBacktrack()
    {
        return numberOfBacktrack;
    }

    public int getNumberOfNodesVisited() {
        return numberOfNodesVisited+1;
    }

    public int getNumberOfNodesAssigned() {
        return numberOfNodesAssigned;
    }

    public int getNumberOfSolution()
    {
        return count;
    }

    public void clearAll()
    {
        this.numberOfNodesAssigned=0;this.numberOfNodesVisited=0;this.numberOfBacktrack=0;this.count=0;
    }

    protected VAR VARIABLE(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        List<VAR> vars = csp.getVariables().stream().
                filter((v) -> !assignment.contains(v)).collect(Collectors.toList());
        return vars.get(0);
    }

    /**
     * Primitive operation, ordering the engine.csp.domain values of the specified variable.
     */

    protected Iterable<VAL> VALUE(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var) {
            return csp.getDomain(var);
    }

}
