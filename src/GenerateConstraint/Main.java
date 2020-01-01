package GenerateConstraint;

import java.io.PrintStream;

public class Main {

	public static void main(String[] args)throws Exception {
		int n=100;
		CSP csp = new CSP(n);
		PrintStream fileOut = new PrintStream("./constraint/"+n+".txt");
		System.setOut(fileOut);
		System.out.println(csp);
	}

}
