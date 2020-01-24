package Backjumping.csp;
/* hello world this is a test mark */
import Backjumping.util.stack.IntStack;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * This class represents the problem
 * it contains the constraints table and other arrays with info needed by different heuristics
 */

/**
 * Implements a CSP
 *
 * @author user
 */
public class Problem implements Serializable {

    static final long serialVersionUID = 42L;  // this is needed in order to save samples to disk
    public Integer constraint_checks;  // number of constrain checks done
    private Integer n;  // number of variable in problem
    private Integer d;  // size of domain
    // for each value in domain false of there is a constraint
    // true if there is not - in the case that the variables are not
    // constrained at all constraints[i][j] is null
    // is incremented when check is called
    // when get_conflicts* functions are called
    // when data is setup for fast conflicts evaluation
    private boolean[][] constraints[][]; // contains for each pair of variables
    // with the other variables (this is filled upon request
    // only when setup_conflict_count is called - and this happens the first time
    // get_conflicts is called
    private Integer conflict_count[]; // for each variable it counts the number of constraints the variable has
    private Integer conflict_count_for_val[][];  // for each 2 variables count the nuber of conflicts between them


    /**
     * create an instance of a problem with n variables and domain size d
     * @param n
     * @param d
     * @param p1
     * @param p2
     */

    /**
     * create an queens instance
     *
     * @param n
     */
    public Problem(Integer n) {
        
        init(n, n);

        for (Integer v1 = 0; v1 < n; v1++)
            for (Integer v2 = 0; v2 < n; v2++) {
                constraints[v1][v2] = new boolean[d][d];
                for (Integer d1 = 0; d1 < d; d1++)
                    for (Integer d2 = 0; d2 < d; d2++) {
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

    /**
     * @return n
     */
    public Integer getN() {
        return n;
    }

    /**
     * @return d
     */
    public Integer getD() {
        return d;
    }


    /**
     * this is common to all the constructors
     *
     * @param n
     * @param d
     */
    private void init(int n, int d) {
        this.n = n;
        this.d = d;

        constraint_checks = 0;
        constraints = new boolean[n][n][][];
    }


    /**
     * read the lines from a file in the format that was given in examples
     * @param v1
     * @param v2
     * @param line
     */

    /**
     * This is the function that does the actual constraint checks
     *
     * @param var1
     * @param val1
     * @param var2
     * @param val2
     * @return true if there is no constraint between var1=val1 and val2=var2
     * false if these two are not permitted together
     */
    public boolean check(int var1, int val1, int var2, int val2) {
        constraint_checks++;
        //System.out.println (var1 +  ", "+  val1 + ", " + var2 +  ", " + val2);
        if (constraints[var1][var2] == null)
            return true;

        return constraints[var1][var2][val1][val2];
    }


    /**
     * prints out the constrains (in a format similar to the examples we got)
     *
     * @param output
     */

    public void printProblem(PrintStream output) {
        output.println("n is: " + n);
        output.println("d is: " + d);

        for (int v1 = 0; v1 < n; v1++)
            for (int v2 = v1 + 1; v2 < n; v2++) {
                if (constraints[v1][v2] != null) {
                    output.print("\nVariables Pairs = ");
                    output.println(v1 + "---" + v2 + ":");
                    output.println("Conflicted Values ");
                    for (int d1 = 0; d1 < d; d1++) {
                        IntStack stack = new IntStack();
                        for (int d2 = 0; d2 < d; d2++) {
                            if (!constraints[v1][v2][d1][d2])
                                stack.push(d2);
                        }

                        if (!stack.isEmpty()) {
                            output.print("          " + d1 + ":[");
                            output.print(stack.peek(0));
                            for (int i = 1; i < stack.size(); i++) {
                                output.print("," + stack.peek(i));
                            }
                            output.println("]");

                        }
                    }
                }
            }

    }


    /**
     * creates an array where for each variable the number of conflicts is held
     * each access to constraints[v1][v2]... is counted as a constraint check
     */
    public void setup_conflict_count() {
        conflict_count = new Integer[n];

        for (int v1 = 0; v1 < n; v1++)
            for (int v2 = 0; v2 < n; v2++) {
                // need to count constraint checks since this is what we are doing here
                constraint_checks++;
                if (constraints[v1][v2] == null)
                    continue;
                for (int d1 = 0; d1 < d; d1++)
                    for (int d2 = 0; d2 < d; d2++) {
                        if (!constraints[v1][v2][d1][d2]) {
                            conflict_count[v1]++;
                            conflict_count[v2]++;
                        }
                    }
            }

    }

    /**
     * @param i
     * @return the number of conflicts variable i has
     */
    public int get_conflicts(int i) {
        if (conflict_count == null)
            setup_conflict_count();

        constraint_checks++;
        return conflict_count[i];

    }

    /**
     * creates an array counting the number of conflicts that one variable has for each
     * value in the domain
     */
    public void setup_conflict_count_for_val() {
        conflict_count_for_val = new Integer[n][d];


        for (int v1 = 0; v1 < n; v1++)
            for (int v2 = 0; v2 < n; v2++) {
                // need to count constraint checks since this is what we are doing here
                constraint_checks++;
                if (constraints[v1][v2] == null)
                    continue;
                for (int d1 = 0; d1 < d; d1++)
                    for (int d2 = 0; d2 < d; d2++) {
                        if (!constraints[v1][v2][d1][d2]) {
                            conflict_count_for_val[v1][d1]++;
                            conflict_count_for_val[v2][d2]++;
                        }
                    }
            }

    }

    /**
     * @param i
     * @param v
     * @return the number of conflicts variable i has for value v
     */
    public int get_conflicts_for_val(int i, int v) {
        if (conflict_count_for_val == null)
            setup_conflict_count_for_val();

        constraint_checks++;
        return conflict_count_for_val[i][v];

    }

}
// comment