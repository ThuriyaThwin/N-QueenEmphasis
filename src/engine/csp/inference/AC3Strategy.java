package engine.csp.inference;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import engine.csp.domain.Domain;
import engine.csp.domain.DomainLog;
import util.QueueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.3, Page 209.<br>
 * <br>
 *
 * <pre>
 * <code>
 * function AC-3(engine.csp) returns false if an inconsistency is found and true otherwise
 *    inputs: engine.csp, a binary CSP with components (X, D, C)
 *    local variables: queue, a queue of arcs, initially all the arcs in engine.csp
 *    while queue is not empty do
 *       (Xi, Xj) = REMOVE-FIRST(queue)
 *       if REVISE(engine.csp, Xi, Xj) then
 *          if size of Di = 0 then return false
 *             for each Xk in Xi.NEIGHBORS - {Xj} do
 *                add (Xk, Xi) to queue
 *    return true
 *
 * function REVISE(engine.csp, Xi, Xj) returns true iff we revise the engine.csp.domain of Xi
 *    revised = false
 *    for each x in Di do
 *       if no value y in Dj allows (x ,y) to satisfy the constraint between Xi and Xj then
 *          delete x from Di
 *          revised = true
 *    return revised
 * </code>
 * </pre>
 * <p>
 * Figure 6.3 The arc-consistency algorithm AC-3. After applying AC-3, either
 * every arc is arc-consistent, or some variable has an empty engine.csp.domain, indicating
 * that the CSP cannot be solved. The name "AC-3" was used by the algorithm's
 * inventor (Mackworth, 1977) because it's the third version developed in the
 * paper.*
 */
public class AC3Strategy<VAR extends Variable, VAL> implements InferenceStrategy<VAR, VAL> {

    /**
     * Makes a CSP consisting of binary engine.csp.constraints arc-consistent.
     *
     * @return An object which indicates success/failure and contains data to
     * undo the operation.
     */
    public InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp) {
        // local variables: queue, a queue of arcs,
        // initially all the arcs in csp.
        Queue<VAR> queue = QueueFactory.createFifoQueueNoDuplicates();
        queue.addAll(csp.getVariables());
        DomainLog<VAR, VAL> log = new DomainLog<>();
        reduceDomains(queue, csp, log);
        return log.compactify();
    }

    /**
     * Reduces the engine.csp.domain of the specified variable to the specified value and
     * reestablishes arc-consistency. It is assumed that the provided CSP was
     * arc-consistent before the call.
     *
     * @return An object which indicates success/failure and contains data to
     * undo the operation.
     */
    public InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var) {
        Domain<VAL> domain = csp.getDomain(var);
        VAL value = assignment.getValue(var);
        assert domain.contains(value);
        DomainLog<VAR, VAL> log = new DomainLog<>();
        if (domain.size() > 1) {
            Queue<VAR> queue = QueueFactory.createFifoQueue();
            queue.add(var);
            log.storeDomainFor(var, domain);
            csp.setDomain(var, new Domain<>(value));
            reduceDomains(queue, csp, log);
        }
        return log.compactify();
    }

    /**
     * For efficiency reasons the queue manages updated variables vj whereas the original AC3
     * manages neighbor arcs (vi, vj). Constraints which are not binary are ignored.
     */

    private void reduceDomains(Queue<VAR> queue, CSP<VAR, VAL> csp, DomainLog<VAR, VAL> log) {
        // while queue is not empty do
        while (!queue.isEmpty()) {
            // (X_i, X_j) = REMOVE-FIRST(queue)
            VAR var = queue.remove();
            // for each X_k in X_i.NEIGHBORS - {X_j} do
            for (Constraint<VAR, VAL> constraint : csp.getConstraints(var)) {
                VAR neighbor = csp.getNeighbor(var, constraint);
                // if size of D_i = 0 then return false
                if (neighbor != null && revise(neighbor, var, constraint, csp, log)) {
                    if (csp.getDomain(neighbor).isEmpty()) {
                        log.setEmptyDomainFound(true);
                        return;
                    }
                    // add (X_k, X_i) to queue
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Establishes arc-consistency for (xi, xj).
     *
     * @return value true if the engine.csp.domain of xi was reduced.
     */

    private boolean revise(VAR xi, VAR xj, Constraint<VAR, VAL> constraint,
                           CSP<VAR, VAL> csp, DomainLog<VAR, VAL> log) {
        Domain<VAL> currDomain = csp.getDomain(xi);
        List<VAL> newValues = new ArrayList<>(currDomain.size());
        Assignment<VAR, VAL> assignment = new Assignment<>();
        // for each x in D_i do
        for (VAL vi : currDomain) {
            assignment.add(xi, vi);
            for (VAL vj : csp.getDomain(xj)) {
                assignment.add(xj, vj);
                if (constraint.isSatisfiedWith(assignment)) {
                    newValues.add(vi);
                    break;
                }
            }
        }
        // if no value y in D_j allows (x, y) to satisfy the constraint
        // between X_i and X_j then
        if (newValues.size() < currDomain.size()) {//Domain reduction occurs.
            log.storeDomainFor(xi, csp.getDomain(xi));
            csp.setDomain(xi, new Domain<>(newValues));
            return true;
        }
        return false;
    }
}
