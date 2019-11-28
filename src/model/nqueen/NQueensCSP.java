package model.nqueen;


import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.CSP;
import engine.csp.CspListener;
import engine.csp.Variable;
import engine.csp.constraints.DiffNotEqualConstraint;
import engine.csp.domain.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		NQueensCSP nQueensCSP=new NQueensCSP(10);
		Optional assignment;
		FlexibleBacktrackingSolver backtrackingSolver=new FlexibleBacktrackingSolver();
		/*backtrackingSolver.addCspListener(new CspListener() {
			@Override
			public void stateChanged(CSP csp, Assignment assignment, Variable variable) {

				System.out.println("Assignment evolved : " +assignment);
			}
		});*/

		double start = System.currentTimeMillis();

		assignment=backtrackingSolver.solve(nQueensCSP);
		double end = System.currentTimeMillis();
		System.out.println("Time to solve in second       = " + (end - start) * 0.001 + " s");
		System.out.println("The Solution is :"+assignment.get());
		//System.out.println(backtrackingSolver.getNumberOfNodesVisited());
	}
}