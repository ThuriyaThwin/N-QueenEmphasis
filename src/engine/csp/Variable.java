package engine.csp;

/**
 * A variable is a distinguishable object with a name.
 */

public class Variable {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    /**
     * Variables with equal names are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && this.name.equals(((Variable) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
