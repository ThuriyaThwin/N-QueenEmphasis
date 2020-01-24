package nqueens;

import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.constraints.DiffNotEqualConstraint;
import engine.csp.domain.Domain;

import java.util.ArrayList;
import java.util.List;

public class NQueensCSP extends CSP<Variable, Integer> {
    private static CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();

    public NQueensCSP(int size) {
        for (int i = 0; i < size; i++)
            addVariable(new Variable("Q" + (i + 1)));

        List<Integer> values = new ArrayList<>();
        for (int val = 1; val <= size; val++)
            values.add(val);
        Domain<Integer> positions = new Domain<>(values);

        for (Variable var : getVariables())
            setDomain(var, positions);

        for (int i = 0; i < size; i++) {
            Variable var1 = getVariables().get(i);
            for (int j = i + 1; j < size; j++) {
                Variable var2 = getVariables().get(j);
                //addConstraint(new DiffNotEqualConstraint(var1, var2, 0));
                addConstraint(new DiffNotEqualConstraint(var1, var2, j - i));
            }
        }
    }

}