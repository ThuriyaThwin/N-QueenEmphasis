package model;


import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.NotEqualConstraint;
import engine.csp.domain.Domain;

import java.util.ArrayList;
import java.util.List;


public class MapCSPMyanmar extends CSP<Variable,String> {
	public static final Variable Kachin = new Variable("Kachin");
	public static final Variable Kayah = new Variable("Kayah");
	public static final Variable Kayin = new Variable("Kayin");
	public static final Variable Chin = new Variable("Chin");
	public static final Variable Mon = new Variable("Mon");
	public static final Variable RaKhaing = new Variable("RaKhaing");
	public static final Variable Shan = new Variable("Shan");
	public static final Variable Sagaing = new Variable("Sagaing");
	public static final Variable Mandalay = new Variable("Mandalay");
	public static final Variable Magwe = new Variable("Magwe");
	public static final Variable Bago = new Variable("Bago");
	public static final Variable Yangon = new Variable("Yangon");
	public static final Variable Ayeyarwady = new Variable("Ayeyarwady");
	public static final Variable Taninthargyi = new Variable("Taninthargyi");
	public static final String RED = "RED";
	public static final String GREEN = "GREEN";
	public static final String BLUE = "BLUE";
	public static final String YELLOW="YELLOW";


	private static List<Variable> collectVariables() {
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(Kachin);//
		variables.add(Kayah);//
		variables.add(Kayin);//
		variables.add(Chin);//
		variables.add(Mon);//
		variables.add(RaKhaing);//
		variables.add(Shan);//
		variables.add(Sagaing);//
		variables.add(Mandalay);//
		variables.add(Magwe);//
		variables.add(Bago);//
		variables.add(Yangon);//
		variables.add(Ayeyarwady);//
		variables.add(Taninthargyi);//

		return variables;
	}


	public MapCSPMyanmar() {
		super(collectVariables());

		Domain colors = new Domain(new Object[] { RED, GREEN, BLUE,YELLOW });

		for (Variable var : getVariables())
			setDomain(var, colors);

		addConstraint(new NotEqualConstraint(Kachin, Sagaing));
		addConstraint(new NotEqualConstraint(Kachin, Shan));
		addConstraint(new NotEqualConstraint(Sagaing, Chin));
		addConstraint(new NotEqualConstraint(Sagaing, Mandalay));
		addConstraint(new NotEqualConstraint(Sagaing, Magwe));
		addConstraint(new NotEqualConstraint(Sagaing, Shan));
		addConstraint(new NotEqualConstraint(Chin, RaKhaing));
		addConstraint(new NotEqualConstraint(Chin, Mandalay));
		addConstraint(new NotEqualConstraint(Chin, Magwe));
		addConstraint(new NotEqualConstraint(RaKhaing,Magwe));
		addConstraint(new NotEqualConstraint(RaKhaing,Ayeyarwady));
		addConstraint(new NotEqualConstraint(RaKhaing,Bago));
		addConstraint(new NotEqualConstraint(Magwe,Mandalay));
		addConstraint(new NotEqualConstraint(Magwe,Bago));
		addConstraint(new NotEqualConstraint(Mandalay,Shan));
		addConstraint(new NotEqualConstraint(Mandalay,Bago));
		addConstraint(new NotEqualConstraint(Mandalay,Kayin));
		addConstraint(new NotEqualConstraint(Mandalay,Kayah));
		addConstraint(new NotEqualConstraint(Shan,Kayin));
		addConstraint(new NotEqualConstraint(Shan,Kayah));
		addConstraint(new NotEqualConstraint(Ayeyarwady,Bago));
		addConstraint(new NotEqualConstraint(Ayeyarwady,Yangon));
		addConstraint(new NotEqualConstraint(Bago,Yangon));
		addConstraint(new NotEqualConstraint(Bago,Mon));
		addConstraint(new NotEqualConstraint(Bago,Kayin));
		addConstraint(new NotEqualConstraint(Kayah,Kayin));
		addConstraint(new NotEqualConstraint(Kayin,Mon));
		addConstraint(new NotEqualConstraint(Mon,Taninthargyi));
	}
}