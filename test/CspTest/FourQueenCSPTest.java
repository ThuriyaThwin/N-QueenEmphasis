package CspTest;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import nqueens.NQueensCSP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


public class FourQueenCSPTest {
    private CSP csp;
    FlexibleBacktrackingSolver backtrackingSolver;

    @Before
    public void setUp() {
        csp = new NQueensCSP(4);
        backtrackingSolver = new FlexibleBacktrackingSolver();
    }

    @Test
    public void testBackTrackingSearch() {
        System.out.println("Case 1: Testing 4-Queens Problem with BT");
        Optional<Assignment> assignment = backtrackingSolver.solve(csp);

        //Test case
        Assignment expected = new Assignment();
        expected.add(new Variable("Q1"), 2);
        expected.add(new Variable("Q2"), 4);
        expected.add(new Variable("Q3"), 1);
        expected.add(new Variable("Q4"), 3);
        Assert.assertEquals(expected.toString(), assignment.get().toString());
    }

   @Test
    public void solveAssignment()
   {
       System.out.println("Case 2: Testing 4-Queens with Specific Assignment");
       Assignment expected = new Assignment();
       expected.add(new Variable("Q1"), 3);
       expected.add(new Variable("Q2"), 1);
       expected.add(new Variable("Q3"), 4);
       Optional<Assignment> assignment = backtrackingSolver.solveSpecific(csp,expected);
       System.out.println("Solution is : "+assignment.get());
   }

}
