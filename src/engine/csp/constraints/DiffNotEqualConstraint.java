package engine.csp.constraints;


import engine.csp.Assignment;
import engine.csp.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which forbids equal values.
 *
 */
public class DiffNotEqualConstraint implements Constraint<Variable, Integer> {

    private Variable var1;
    private Variable var2;
    private int diff;
    private List<Variable> scope;
    private boolean bol;
    public static int constraintcheck;
    public static int overcheck;
    Integer value1;
    Integer value2;
    public DiffNotEqualConstraint(Variable var1, Variable var2, int diff) {
        this.var1 = var1;
        this.var2 = var2;
        this.diff = diff;
        scope = new ArrayList<Variable>(2);
        scope.add(var1);
        scope.add(var2);
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
        constraintcheck++;
        value1 = assignment.getValue(var1);
        value2 = assignment.getValue(var2);

        try {
            if (Math.abs(value1 - value2) != diff && Math.abs(value1 - value2) != 0) {
              //  System.out.println("Value " + value1 + " " + value2);
                bol=true;
            }
        } catch (Exception e) {
            overcheck++;
        }
        return (value1 == null || value2 == null || Math.abs(value1 - value2) != diff && Math.abs(value1 - value2) != 0);
    }


    public boolean OisSatisfiedWith(Assignment<Variable, Integer> assignment) {

        value1 = assignment.getValue(var1);
        value2 = assignment.getValue(var2);

        try {
            if (Math.abs(value1 - value2) != diff && Math.abs(value1 - value2) != 0) {
                System.out.println("Value " + value1 + " " + value2);
                bol=true;
            }
        } catch (Exception e) {
            overcheck++;
        }
        return (value1 == null || value2 == null || Math.abs(value1 - value2) != diff && Math.abs(value1 - value2) != 0);
    }
    @Override
    public String toString() {
        return "[" + var1.getName() + " , " + var2.getName() + "]" + System.lineSeparator();
    }
}
