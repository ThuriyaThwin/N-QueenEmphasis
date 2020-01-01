import engine.algo.BackTrackingSolver;
import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.constraints.DiffNotEqualConstraint;
import engine.csp.inference.ForwardCheckingStrategy;
import model.nqueen.NQueensCSP;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class ConsoleApp {
    public static void main(String args[]) throws IOException {

        NQueensCSP csp=new NQueensCSP(17);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
      //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        //Runtime runtime = Runtime.getRuntime();//for memory

        FlexibleBacktrackingSolver bts=new FlexibleBacktrackingSolver();
        bts.set(CspHeuristics.mrv());
        bts.set(new ForwardCheckingStrategy());
       // bts.set(CspHeuristics.mrv());
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
        System.out.println(DiffNotEqualConstraint.constraintcheck-DiffNotEqualConstraint.overcheck);
     // System.out.println("Used Memory                   = " + ((runtime.totalMemory() - runtime.freeMemory())) / (double) 1000000 + " mb");
    }
}
