package engine.generateConstraint;

import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws Exception {
        int n = 4;
        CSP csp = new CSP(n);
        PrintStream fileOut = new PrintStream("./constraint/" + n + ".txt");
        System.setOut(fileOut);
        System.out.println(csp);
    }

}
