package console;

import engine.algo.backJumping.csp.Problem;
import engine.algo.backJumping.prosser.Bcssp;
import engine.algo.backJumping.prosser.CBJ;
import util.StoreResult;
import util.Timer;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class BJConsole {
    public static void main(String args[]) throws Exception {
        //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        Problem p = new Problem(StoreResult.size);
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));

        CBJ a = new CBJ(p);
//        a.label(1);
        Timer.tic();
        a.bcssp();//Like a.solve()*/
        System.out.println("Time to solve in second        = "+ Timer.toc()+"s");
        System.out.println("Number of nodes Visited        = "+ Bcssp.assignments+1);
        System.out.println("The solution in CSP form       = "+a.printV());


    }
}
