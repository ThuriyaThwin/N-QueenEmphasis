package engine.csp;

import engine.csp.constraints.Constraint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An assignment assigns values to some or all variables of a CSP.
 *
 */

public class Assignment<VAR extends Variable, VAL> implements Cloneable {
    public static int count=0;
    boolean[][] constraints[][];
    /**
     * Maps variables to their assigned values.
     */

    private LinkedHashMap<VAR, VAL> variableToValueMap = new LinkedHashMap<>();

    public List<VAR> getVariables() {
        return variableToValueMap.keySet().stream().collect(Collectors.toList());
    }

    public VAL getValue(VAR var) {
        return variableToValueMap.get(var);
    }

    public VAL add(VAR var, VAL value) {
        assert value != null;
        return variableToValueMap.put(var, value);
    }

    public VAL remove(VAR var) {
        return variableToValueMap.remove(var);
    }

    public boolean contains(VAR var) {
        return variableToValueMap.containsKey(var);
    }

    /**
     * Returns true if this assignment does not violate any engine.csp.constraints of
     * <code>engine.csp.constraints</code>.
     */
    public void s(CSP csp) {
        int n=csp.getVariables().size();
          // contains for each pair of variables

        for (int v1 = 0; v1 < n; v1++)
            for (int v2 = 0; v2 < n; v2++) {
                constraints[v1][v2] = new boolean[n][n];
                for (int d1 = 0; d1 < n; d1++)
                    for (int d2 = 0; d2 < n; d2++) {
                        if (d1 == d2)
                            constraints[v1][v2][d1][d2] = false;
                        else if ((d1 - v1) == (d2 - v2))
                            constraints[v1][v2][d1][d2] = false;
                        else if ((d1 + v1) == (d2 + v2))
                            constraints[v1][v2][d1][d2] = false;
                        else
                            constraints[v1][v2][d1][d2] = true;
                    }
            }

    }

    public boolean isConsistent(List<Constraint<VAR, VAL>> constraints) {


        for (Constraint<VAR, VAL> cons : constraints) {
            System.out.println("Assignment :"+this);
            System.out.println("Constraint :"+cons+"\n..........................");
            if (!cons.isSatisfiedWith(this))
                return false;
        }
        return true;
    }

    /**
     * Returns true if this assignment assigns values to every variable of
     * <code>vars</code>.
     */
    public boolean isComplete(List<VAR> vars) {
        for (VAR var : vars)
            if (!contains(var))
                return false;
        return true;
    }

    /**
     * Returns true if this assignment is consistent as well as complete with
     * respect to the given CSP.
     */

    public boolean isSolution(CSP<VAR, VAL> csp) {
        return isConsistent(csp.getConstraints()) && isComplete(csp.getVariables());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Assignment<VAR, VAL> clone() {
        Assignment<VAR, VAL> result;
        try {
            result = (Assignment<VAR, VAL>) super.clone();
            result.variableToValueMap = new LinkedHashMap<>(variableToValueMap);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException("Could not clone assignment."); // should never happen!
        }
        return result;
    }


    @Override
    public String toString() {
        boolean comma = false;
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<VAR, VAL> entry : variableToValueMap.entrySet()) {
            if (comma)
                result.append(", ");
            result.append(entry.getKey()).append("=").append(entry.getValue());
            comma = true;
        }
        result.append("}");
        return result.toString();
    }


}