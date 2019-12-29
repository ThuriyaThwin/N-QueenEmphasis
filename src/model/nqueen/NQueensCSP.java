package model.nqueen;


import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.constraints.DiffNotEqualConstraint;
import engine.csp.domain.Domain;
import util.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class NQueensCSP extends CSP<Variable, Integer> {
	private static CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
	public NQueensCSP(int size) {
		for (int i = 0; i < size; i++)
			addVariable(new Variable("Q" + (i+1)));
		
		List<Integer> values = new ArrayList<>();
		for (int val = 1; val <= size; val++)
			values.add(val);
		Domain<Integer> positions = new Domain<>(values);

		for (Variable var : getVariables())
			setDomain(var, positions);

		for (int i = 0; i < size; i++) {
			Variable var1 = getVariables().get(i);
			for (int j = i+1; j < size; j++) {
				Variable var2 = getVariables().get(j);
				addConstraint(new DiffNotEqualConstraint(var1, var2, 0));
				addConstraint(new DiffNotEqualConstraint(var1, var2, j-i));
			}
		}
	}

	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		boolean check=true;
		while(check)
		{System.out.println("Enter Board Size: ");
		int size=sc.nextInt();
		NQueensCSP nQueensCSP=new NQueensCSP(size);
		System.out.println("Choose Algorithm:");
		System.out.println("1.BT\n2.BJ\n3.FC\n4.AC3-FC\n5.MAC\n6.FC-MRV\nFC-LCV");
		Optional assignment;
		FlexibleBacktrackingSolver backtrackingSolver=new FlexibleBacktrackingSolver();
		backtrackingSolver.addCspListener(new CspListener() {
			@Override
			public void stateChanged(CSP csp, Assignment assignment, Variable variable) {

				//System.out.println("Assignment evolved : " +assignment);
			}
		});

		Timer.tic();
		assignment=backtrackingSolver.solve(nQueensCSP);
		double end = System.nanoTime();
		System.out.println("Time to solve in second       = " + Timer.toc()+ " s");
		System.out.println("The Solution is :"+assignment.get());
		//System.out.println(backtrackingSolver.getNumberOfNodesVisited());
			System.out.println("Enter another? y/n");
			String you=sc.next();
			if(you.equals("n")){
				check=false;
			}
	}
	}
}