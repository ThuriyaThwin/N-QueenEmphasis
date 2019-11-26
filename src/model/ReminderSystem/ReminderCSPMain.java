package model.ReminderSystem;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReminderCSPMain {
    /**
     * Main class of CSP
     */
        List<Object[]> domains = new ArrayList<>();


        public static void main(String[] args) throws Exception{

            double startTime = System.currentTimeMillis();
            Runtime runtime = Runtime.getRuntime();

            // Getting user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // Getting the variables
            System.out.println("Please put all candidates in a single line each differ with space for this reminder program (MAX 10): ");
            String[] variables = br.readLine().split(" ");
            if (variables.length > 10) {
                throw new Exception("Max limit reached");
            }

            // Getting the domains
            List<Object[]> domains = new ArrayList<>();
            for (int i = 0; i < variables.length; i++) {
                System.out.print("Specify the preferable candidates in a single line differ with space for " + variables[i] + " (MAX 9): ");
                Object[] domain = br.readLine().split(" ");
                System.out.println();
                domains.add(domain);
            }            // End of input


            CSP csp = new ReminderCSP(variables, domains);
            Assignment results = (Assignment) new FlexibleBacktrackingSolver<Variable,String>().solve(csp).get();
            System.out.println(results.toString());

            printUsedTimeMemory(startTime, runtime);
        }

        public static void printUsedTimeMemory(double startTime, Runtime rt){

            float mb = (1024*1024);
            //Print used memory
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(3);
            System.out.println("Used Memory: "
                    + df.format((rt.totalMemory() - rt.freeMemory())/mb) + " Mb");

            //.Print computation time
            double endTime = System.currentTimeMillis();

            double time = (endTime - startTime)/1000;
            System.out.println("Time: " + df.format(time) + " s");
        }


    }

/*
 *
 *
A B C D E F G H I J

F G J C H E D
F J B D A C H
I H E C B F A D
B G F A E H J
G H E D I C
F C H D A E I
B E J G C
A E J C B
E I B C J D A H F
G
 *
*/

