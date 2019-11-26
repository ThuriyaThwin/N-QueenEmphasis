package model.SodokuApp;


import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.inference.ForwardCheckingStrategy;

import java.awt.*;
import java.io.*;
import java.util.Optional;


public class SodokuCSPSolver {
	public static void main(String[] args) {
		// Create a CSP Problem
		SodokuVariableCollection var = new SodokuVariableCollection("/home/thuriya/IdeaProjects/FinalThesis/src/model/SodokuApp/sodoku/sodoku2.ucsm");

		SodokuCSP csp = new SodokuCSP(var);

		System.out.println("\n\n------->>  input  <<-----------");
		System.out.println(var.getString());

		FlexibleBacktrackingSolver solver = new FlexibleBacktrackingSolver();

		System.out.println("Apply Backtracking Strategy: FC+MRV");
		solver.set(new ForwardCheckingStrategy()).set(CspHeuristics.mrv());

		long start = System.currentTimeMillis();
		Optional<Assignment> results = solver.solve(csp);

		long finish = System.currentTimeMillis();

		long diff = finish - start;

		System.out.println("execute time " + (diff / 1000) + " second");

		// Print the output
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				SodokuVariable v = var.get(i, j);
				v.value = (int) results.get().getValue(v);
			}
		}

		System.out.println("\n------->>  result  <<-----------");
		System.out.println(var.getString());
		/*File f = new File("a.html");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(var.getHtml());
			bw.close();
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			return;
		}*/


	}
}
