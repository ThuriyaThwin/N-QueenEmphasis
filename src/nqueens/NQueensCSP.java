package nqueens;

import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.constraints.DiffNotEqualConstraint;
import engine.csp.domain.Domain;
import engine.csp.inference.AC3Strategy;
import engine.csp.inference.ForwardCheckingStrategy;
import util.Util;

import java.io.IOException;
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

    public static void main(String args[]) throws IOException {
        NQueensCSP csp = new NQueensCSP(13);

        //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory
        FlexibleBacktrackingSolver bts = new FlexibleBacktrackingSolver();

        bts.addCspListener(new CspListener() {
            @Override
            public void stateChanged(CSP csp, Assignment assignment, Variable variable) {
                try {
                    System.out.println("Assignment Evolved :" + assignment + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        double start = System.currentTimeMillis();
        Util.setposition(true);
        bts.solveAll(csp);
        double end = System.currentTimeMillis();
        System.out.println("\nTime to solve in second       = " + (end - start) * 0.001 + " s");
        System.out.println(bts.getNumberOfSolution());
        System.out.println("\n" + bts.getNumberOfNodesVisited());
    }
}