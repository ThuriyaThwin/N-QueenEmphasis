import engine.algo.BackTrackingSolver;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.inference.AC3Strategy;
import engine.csp.inference.ForwardCheckingStrategy;
import model.nqueen.NQueensCSP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        NQueensCSP csp=new NQueensCSP(4);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
      //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory

        FlexibleBacktrackingSolver bts=new FlexibleBacktrackingSolver();

        double start = System.currentTimeMillis();
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



        log.write("\nThe solution is     = " + bts.solve(csp));
        log.flush();
        double end = System.currentTimeMillis();
        //System.out.println("\nThe number of solutions is   = "+bts.getNumberOfSolution());
        System.out.println("\nAlgorithm Evaluation process");
        System.out.println("................................\n");
        System.out.println("\nTime to solve in second       = " + (end - start) * 0.001 + " s");
        System.out.println("\nNumber of backtracking occurs = " + bts.getNumberOfBacktrack() + " times");
        System.out.println("\nNumber of nodes visited       = " + bts.getNumberOfNodesVisited() + " nodes");
        System.out.println("\nNumber of nodes assigned      = " + bts.getNumberOfNodesAssigned() + " nodes");

     // System.out.println("Used Memory                   = " + ((runtime.totalMemory() - runtime.freeMemory())) / (double) 1000000 + " mb");
    }
}
