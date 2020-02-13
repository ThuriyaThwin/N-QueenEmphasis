package AlgoTest;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import engine.csp.domain.Domain;
import engine.csp.inference.ForwardCheckingStrategy;
import nqueens.NQueensCSP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FCTest {
    public ForwardCheckingStrategy fc=new ForwardCheckingStrategy();
    private CSP csp;


    @Before
    public void setUp() {
        csp = new NQueensCSP(4);
    }

    @Test
    public void BeforeFCTest()// This shows that FC is not changed at beginning.
    {
        System.out.println("Testing FC over Y=X^2");
        Variable x=new Variable("X");
        Variable y=new Variable("Y");
        CSP<Variable,Integer> csp=new CSP<>(Arrays.asList(x,y));
        csp.setDomain(x, new Domain<>(0,1,2,3,4,5));    Assert.assertEquals(6,csp.getDomain(x).size());
        csp.setDomain(y, new Domain<>(0,1,3,5,9,12,16));
        csp.addConstraint(new Constraint<Variable, Integer>() {
            @Override
            public List<Variable> getScope() {
                return Arrays.asList(x,y);
            }

            @Override
            public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
                return assignment.getValue(y)==assignment.getValue(x)*assignment.getValue(x);
            }
        });

        boolean t= fc.apply(csp).isEmpty();
        System.out.println("Reduced Domain :"+!t);
         Assert.assertTrue(t);
        //Formatting Table
        String line = new String(new char[34]).replace('\0', '-');
        System.out.println(line);
        System.out.format("| %-12S | %-15S |%n","variable","domain");
        System.out.println(line);
        for (Variable var: csp.getVariables()) {
            System.out.format("| %-12S | %-15S |%n", var.getName(),csp.getDomain(var));
        }
        System.out.println(line);

        Assert.assertEquals(6,csp.getDomain(x).size());//Show the reduced domain size
    }

    @Test
    public void AfterTest()
    {
        System.out.println("Testing FC prunes during assignment");
        Assignment assignment=new Assignment();
        Variable v1=(Variable)csp.getVariables().get(0);
        assignment.add(v1,1);
        Variable v2=(Variable)csp.getVariables().get(1);
        boolean ch=fc.apply(csp,assignment,v1).isEmpty();
        System.out.println("Testing :"+ch);
        System.out.println("Q1 :"+csp.getDomain(v1));
        System.out.println("Q2 :"+csp.getDomain(v2));
        System.out.println("Q3 :"+csp.getDomain((Variable)csp.getVariables().get(2)));
        System.out.println("Q4 :"+csp.getDomain((Variable)csp.getVariables().get(3)));
    }
}
