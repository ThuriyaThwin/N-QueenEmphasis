package Backjumping;


import Backjumping.csp.Problem;
import Backjumping.prosser.BT;
import Backjumping.prosser.CBJ;
import Backjumping.prosser.FC_Cbj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws Exception {
      //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        double start = System.currentTimeMillis();
        Problem p=new Problem(20);
        CBJ a = new CBJ(p);
        a.bcssp();//Like a.solve()*/
        double end = System.currentTimeMillis();
        System.out.println("Time to solve in second       = " + (end - start) * 0.001 + " s");
        p.printProblem(System.out);
        System.out.println(a.printV());


    }



}

