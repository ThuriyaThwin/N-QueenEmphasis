package simulation;

import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.inference.ForwardCheckingStrategy;
import nqueens.NQueensCSP;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class ConsoleApp {
    public static void main(String args[]) throws IOException {
        NQueensCSP csp = new NQueensCSP(30);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
          //Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory
        FlexibleBacktrackingSolver bts = new FlexibleBacktrackingSolver();
        bts.set(new ForwardCheckingStrategy()).set(CspHeuristics.lcv());
        double start = System.currentTimeMillis();
        bts.solve(csp);
        double end = System.currentTimeMillis();
        log.write("\nTime to solve in second       = " + (end - start) * 0.001 + " s");
        log.write("\nnodes       = " + bts.getNumberOfNodesVisited());
        log.flush();
        System.out.println("\n" + bts.getNumberOfNodesVisited());
    }
}
