package engine.algo;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.inference.InferenceLog;
import engine.csp.inference.InferenceStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class BJ<VAR extends Variable, VAL> extends BackjumpingSolver<VAR, VAL> {
    private CspHeuristics.VariableSelection<VAR, VAL> varSelectionStrategy;
    private CspHeuristics.ValueSelection<VAR, VAL> valSelectionStrategy;
    private InferenceStrategy<VAR, VAL> inferenceStrategy;
    private CSP<VAR, VAL> csp;
    private Assignment<VAR, VAL> assignment;


    @Override
    protected VAR selectUnassignedVariable(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        this.csp = csp;
        this.assignment = assignment;
        List<VAR> vars = csp.getVariables().stream().
                filter((v) -> !assignment.contains(v)).collect(Collectors.toList());
        if (varSelectionStrategy != null)
            vars = varSelectionStrategy.apply(csp, vars);
        return vars.get(0);
    }

    @Override
    protected Iterable orderDomainValues(CSP csp, Assignment assignment, Variable variable) {
        if (valSelectionStrategy != null)
            return valSelectionStrategy.apply(csp, assignment, (VAR) variable);
        else
            return csp.getDomain(variable);
    }


    @Override
    protected InferenceLog inference(CSP csp, Assignment assignment, Variable variable) {
        return null;
    }
}
