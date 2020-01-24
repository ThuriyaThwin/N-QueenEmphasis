package backJumping;


import backJumping.csp.Problem;
import backJumping.prosser.CBJ;
import util.Timer;

public class Main {
    public static void main(String args[]) throws Exception {
        //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        Problem p = new Problem(10);
        CBJ a = new CBJ(p);
        a.label(1);
        Timer.tic();
        a.bcssp();//Like a.solve()*/
        System.out.println("Time to solve in second " + Timer.toc());
        System.out.println(a.printV());


    }


}

