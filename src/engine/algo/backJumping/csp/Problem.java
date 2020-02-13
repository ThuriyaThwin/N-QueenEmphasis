package engine.algo.backJumping.csp;
/* hello world this is a test mark */

import java.io.Serializable;

/**
 * This class represents the problem
 * it contains the constraints table and other arrays with info needed by different heuristics
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


}
// comment