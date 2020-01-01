package GenerateConstraint;

import java.io.PrintStream;

public class Main {

	public static void main(String[] args)throws Exception {

		CSP csp = new CSP(28);
		PrintStream fileOut = new PrintStream("./out.txt");
		System.setOut(fileOut);
		System.out.println(csp);
	}

}
