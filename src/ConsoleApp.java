import engine.algo.BackTrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import model.nqueen.NQueensCSP;

import java.util.Scanner;

public class ConsoleApp {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        NQueensCSP csp=new NQueensCSP(4);
       // Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory
        double start = System.currentTimeMillis();
        /*FlexibleBacktrackingSolver bts=new FlexibleBacktrackingSolver();
        bts.set(new AC3Strategy()).set(new ForwardCheckingStrategy());*/
        BackTrackingSolver bts=new BackTrackingSolver();
        bts.addCspListener(new CspListener() {
            @Override
            public void stateChanged(CSP csp, Assignment assignment, Variable variable) {
                System.out.println("Assignment Evolved :"+assignment);
            }
        });


        double end = System.currentTimeMillis();
        System.out.println("\nThe solution is     = " + bts.solve(csp));
        //System.out.println("\nThe number of solutions is   = "+bts.getNumberOfSolution());
        System.out.println("\nAlgorithm Evaluation process");
        System.out.println("................................\n");
        System.out.println("Time to solve in second       = " + (end - start) * 0.001 + " s");
       /* System.out.println("Number of backtracking occurs = " + bts.getNumberOfBacktrack() + " times");
        System.out.println("Number of nodes visited       = " + bts.getNumberOfNodesVisited() + " nodes");
        System.out.println("Number of nodes assigned      = " + bts.getNumberOfNodesAssigned() + " nodes");*/
     //   System.out.println("Used Memory                   = " + ((runtime.totalMemory() - runtime.freeMemory())) / (double) 1000000 + " mb");
    }
}
