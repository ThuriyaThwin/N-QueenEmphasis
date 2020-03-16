package console;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import nqueens.NQueensCSP;
import util.StoreResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;


public class BTConsole {

    public static void main(String args[]) throws IOException {
        System.out.println("The Result of Backtracking Algorithm::");
        NQueensCSP csp = new NQueensCSP(StoreResult.size=4);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
          //Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        FlexibleBacktrackingSolver bts = new FlexibleBacktrackingSolver();
        double start = System.currentTimeMillis();
        Optional<Assignment> solution=bts.solve(csp);
        double end = System.currentTimeMillis();
        log.write("\nThe solution in CSP form      = " +solution.get().toString());
        log.write("\nTime to solve in second       = " + (end - start) * 0.001 + " s");
        log.write("\nNumber of nodes Visited       = " + bts.getNumberOfNodesVisited());
        log.flush();
    }
}
