package Backjumping;


import Backjumping.csp.Problem;
import Backjumping.prosser.CBJ;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws Exception {
      //  Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 20, TimeUnit.MINUTES);// Program Timer
        double start = System.currentTimeMillis();

        CBJ a = new CBJ(new Problem(6));
        a.bcssp();//Like a.solve()*/

        double end = System.currentTimeMillis();
        System.out.println("Time to solve in second       = " + (end - start) * 0.001 + " s");
        /*a.printV(new PrintStream("a.txt"));
        a.printV(System.out);*/
        System.out.println(a.printV());
       // writeUsingFileWriter(a.get());

    }

    private static void writeUsingFileWriter(String data) {
        File file = new File("FileWriter.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

