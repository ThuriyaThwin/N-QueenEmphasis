package model;


import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.NotEqualConstraint;
import engine.csp.domain.Domain;

import java.util.Arrays;


public class MapCSPAutstralia extends CSP<Variable, String> {
	public static final Variable NSW = new Variable("NSW");
	public static final Variable NT = new Variable("NT");
	public static final Variable Q = new Variable("Q");
	public static final Variable SA = new Variable("SA");
	public static final Variable T = new Variable("T");
	public static final Variable V = new Variable("V");
	public static final Variable WA = new Variable("WA");
	public static final String RED = "RED";
	public static final String GREEN = "GREEN";
	public static final String BLUE = "BLUE";

	/**
	 * Constructs a map CSP for the principal states and territories of
	 * Australia, with the colors Red, Green, and Blue.
	 */
	public MapCSPAutstralia() {
		super(Arrays.asList(NSW, WA, NT, Q, SA, V, T));

		Domain<String> colors = new Domain<>(RED, GREEN, BLUE);
		for (Variable var : getVariables())
			setDomain(var, colors);
		addConstraint(new NotEqualConstraint<>(WA, NT));
		addConstraint(new NotEqualConstraint<>(WA, SA));
		addConstraint(new NotEqualConstraint<>(NT, SA));
		addConstraint(new NotEqualConstraint<>(NT, Q));
		addConstraint(new NotEqualConstraint<>(SA, Q));
		addConstraint(new NotEqualConstraint<>(SA, NSW));
		addConstraint(new NotEqualConstraint<>(SA, V));
		addConstraint(new NotEqualConstraint<>(Q, NSW));
		addConstraint(new NotEqualConstraint<>(NSW, V));
	}
}