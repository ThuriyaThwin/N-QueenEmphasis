package engine.csp.domain;

import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.inference.InferenceLog;
import util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Provides information which might be useful for a caller of a constraint
 * propagation algorithm. It maintains old domains for variables and provides
 * means to restore the initial state of the CSP (before engine.csp.domain reduction
 * started). Additionally, a flag indicates whether an empty engine.csp.domain has been
 * found during propagation.
 * *
 */

public class DomainLog<VAR extends Variable, VAL> implements InferenceLog<VAR, VAL> {
    private List<Pair<VAR, Domain<VAL>>> savedDomains;
    private HashSet<VAR> affectedVariables;
    private boolean emptyDomainObserved;

    public DomainLog() {
        savedDomains = new ArrayList<>();
        affectedVariables = new HashSet<>();
    }


    /**
     * Stores the specified engine.csp.domain for the specified variable if a engine.csp.domain has
     * not yet been stored for the variable.
     */
    public void storeDomainFor(VAR var, Domain<VAL> domain) {
        if (!affectedVariables.contains(var)) {
            savedDomains.add(new Pair<>(var, domain));
            affectedVariables.add(var);
        }
    }

    private List<Pair<VAR, Domain<VAL>>> getSavedDomains() {
        return savedDomains;
    }


    public void setEmptyDomainFound(boolean b) {
        emptyDomainObserved = b;
    }

    /**
     * Can be called after all engine.csp.domain information has been collected to reduce
     * storage consumption.
     *
     * @return this object, after removing one hashtable.
     */

    public DomainLog<VAR, VAL> compactify() {
        affectedVariables = null;
        return this;
    }

    @Override
    public boolean isEmpty() {
        return savedDomains.isEmpty();
    }

    @Override
    public void undo(CSP<VAR, VAL> csp) {
        for (Pair<VAR, Domain<VAL>> pair : getSavedDomains())
            csp.setDomain(pair.getFirst(), pair.getSecond());
    }

    @Override
    public boolean inconsistencyFound() {
        return emptyDomainObserved;
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pair<VAR, Domain<VAL>> pair : savedDomains)
            result.append(pair.getFirst()).append("=").append(pair.getSecond()).append(" ");
        if (emptyDomainObserved)
            result.append("!");
        return result.toString();
    }
}
