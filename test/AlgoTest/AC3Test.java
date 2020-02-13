package AlgoTest;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import engine.csp.domain.Domain;
import engine.csp.inference.AC3Strategy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

public class AC3Test {
    public AC3Strategy ac3=new AC3Strategy();

    @Test
    public void testAC3()
    {
        System.out.println("Testing AC3 over Y=X^2");
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

        boolean c= ac3.apply(csp).isEmpty();
        System.out.println("Reduced Domain :"+!c);
        Assert.assertFalse(c);
        //Formatting Table
        String line = new String(new char[34]).replace('\0', '-');
        System.out.println(line);
        System.out.format("| %-12S | %-15S |%n","variable","domain");
        System.out.println(line);
        for (Variable var: csp.getVariables()) {
            System.out.format("| %-12S | %-15S |%n", var.getName(),csp.getDomain(var));
        }
        System.out.println(line);

        Assert.assertEquals(4,csp.getDomain(x).size());//Show the reduced domain size
    }


}
