package console;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.inference.AC3Strategy;
import engine.csp.inference.ForwardCheckingStrategy;
import nqueens.NQueensCSP;
import util.StoreResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;

public class MAC3Console {

    public static void main(String args[]) throws IOException {
        System.out.println("The Result of MAC3 Algorithm::");
        NQueensCSP csp = new NQueensCSP(StoreResult.size);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
        //Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory
        FlexibleBacktrackingSolver bts = new FlexibleBacktrackingSolver();
        bts.set(new AC3Strategy());
        double start = System.currentTimeMillis();
        Optional<Assignment> solution=bts.solve(csp);
        double end = System.currentTimeMillis();
        log.write("\nThe solution in CSP form      = " +solution.get().toString());
        log.write("\nTime to solve in second       = " + (end - start) * 0.001 + " s");
        log.write("\nNumber of nodes Visited       = " + bts.getNumberOfNodesVisited());
        log.flush();
    }
}
