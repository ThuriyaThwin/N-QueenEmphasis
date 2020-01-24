package Simulation;

import com.google.common.base.Strings;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;


import model.nqueen.NQueensCSP;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class ConsoleApp {
    public static void main(String args[]) throws IOException {

        NQueensCSP csp=new NQueensCSP(12);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
      //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory
        FlexibleBacktrackingSolver bts=new FlexibleBacktrackingSolver();
       //  bts.set(CspHeuristics.mrv());
     //   bts.set(new ForwardCheckingStrategy()).set(CspHeuristics.mrv());
        bts.addCspListener(new CspListener() {
            @Override
            public void stateChanged(CSP csp, Assignment assignment, Variable variable) {
                try {
                   log.write("Assignment Evolved :"+assignment+"\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        double start = System.currentTimeMillis();
        log.write("\nThe solution is     = " + bts.solve(csp).get());
        double end = System.currentTimeMillis();
        log.write("\nTime to solve in second       = " + (end - start) * 0.001 + " s");

        log.flush();
        System.out.println("\n"+bts.getNumberOfNodesVisited());
        

        String line = new String(new char[101]).replace('\0', '-');
        System.out.println(line);
        System.out.format("| %-9S | %18S |%66S|\n", "variables", Strings.padEnd("domains", 18,' '),
                Strings.padEnd("corresponding constraints", 50,' '));
        System.out.println(line);
        csp.getVariables().forEach(
                var -> System.out.format("| %-9s | %-18s | %-64s |%n", var,
                        csp.getDomain(var), csp.getConstraints(var))
        );
        System.out.println(line);
       /* log.write("\nThe solution is     = " + bts.solve(csp));
        log.flush();
        double end = System.currentTimeMillis();
        //System.out.println("\nThe number of solutions is   = "+bts.getNumberOfSolution());
        System.out.println("\nAlgorithm Evaluation process");
        System.out.println("................................\n");
        System.out.println("\nTime to solve in second       = " + (end - start) * 0.001 + " s");*/
       // System.out.println("\nNumber of backtracking occurs = " + bts.getNumberOfBacktrack() + " times");
        /*for(int i=0;i<csp.getVariables().size();i++)
        {
            System.out.println(csp.getConstraints(csp.getVariables().get(i)).toString());
        }*/
    }
}
