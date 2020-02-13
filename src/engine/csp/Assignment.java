package engine.csp;

import engine.csp.constraints.Constraint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * An assignment assigns values to some or all variables of a CSP.
 */

public class Assignment<VAR extends Variable, VAL> implements Cloneable {
    public static int count = 0;
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

    public boolean isConsistent(List<Constraint<VAR, VAL>> constraints) {
        for (Constraint<VAR, VAL> cons : constraints) {
            //   System.out.println("Assignment :"+this);
            //  System.out.println("Constraint :"+cons+"\n..........................");
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
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        for (Map.Entry<VAR, VAL> entry : variableToValueMap.entrySet()) {
            sj.add(entry.getKey() + "=" + entry.getValue());
        }
        return sj.toString();
    }


}