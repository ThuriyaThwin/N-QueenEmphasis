package engine.algo;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspSolver;
import engine.csp.Variable;
import engine.csp.inference.InferenceLog;
import util.Tasks;
import util.Timer;
import util.Util;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

public abstract class BackjumpingSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {
    static int count = 0;
    static double time = 0;
    boolean solveAll = false;
    private int numberOfBacktrack = 0;
    private int test = 0;
    private int numberOfNodesVisited = 0;
    private int numberOfNodesAssigned = 0;
    public ArrayList<TreeSet<Variable>> varArrayList = new ArrayList<TreeSet<Variable>>();//Building a conflict-set

    /**
     * Applies a recursive backtracking search to solve the CSP.
     */
    public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
        Timer.tic();
        for (int i = 0; i < csp.getVariables().size(); i++)
            varArrayList.add(new TreeSet<>());
        Assignment<VAR, VAL> result = backjump(csp, new Assignment<>());
        time = Timer.toc();
        this.solveAll = false;
        return result != null ? Optional.of(result) : Optional.empty();
    }

    public Optional<Assignment<VAR, VAL>> solveAll(CSP<VAR, VAL> csp) {
        this.clearAll();
        this.solveAll = true;

        return this.solve(csp);
    }

    // function BACKTRACK(assignment, csp) returns a solution, or failure
    private Assignment<VAR, VAL> backjump(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        Assignment<VAR, VAL> result = null;
        // if assignment is complete then return assignment
        if (assignment.isComplete(csp.getVariables()) || Tasks.currIsCancelled()) {
            result = assignment;
        } else {
            // var <- SELECT-UNASSIGNED-VARIABLE(assignment, csp)
            VAR var = selectUnassignedVariable(csp, assignment);

            // for each value in ORDER-DOMAIN-VALUES(var, assignment, csp) do
            for (VAL value : orderDomainValues(csp, assignment, var)) {
                // if value is consistent with assignment then
                assignment.add(var, value);
                numberOfNodesVisited++;
                fireStateChanged(csp, assignment, var);
                if (assignment.isConsistent(csp.getConstraints(var))) {
                    numberOfNodesAssigned++;
                    // inferences <- INFERENCE(csp, assignment, var, value)

                    result = backjump(csp, assignment);
                    // if result != failure then
                    if (result != null)
                        // return result
                        break;
                    if (result == null) {
                        numberOfBacktrack++;
                    }
                } else {
                //  varArrayList.get(0).addAll(assignment.getVariables());
                }
            }

            // remove {var = value} from assignment
            assignment.remove(var);
        }

        // return failure
        return result;
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
