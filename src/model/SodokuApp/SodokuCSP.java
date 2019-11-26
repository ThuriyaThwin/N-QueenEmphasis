package model.SodokuApp;


import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.domain.Domain;

public class SodokuCSP extends CSP<Variable,Integer> {

	public SodokuCSP(SodokuVariableCollection listVariable) {
		super(listVariable);

		Domain domain = new Domain(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		for (Variable var : getVariables()) {
			if (var instanceof SodokuVariable) {
				if (((SodokuVariable) var).value != 0) {
					// set only one variable for fixed variable
					setDomain(var, new Domain(new Integer[] { ((SodokuVariable) var).value }));
				} else {
					// set 1->9 domain
					setDomain(var, domain);
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			SodokuConstraint constraint_row = new SodokuConstraint();
			SodokuConstraint constraint_column = new SodokuConstraint();

			for (int j = 0; j < 9; j++) {
				constraint_row.add(listVariable.get(i, j));
				constraint_column.add(listVariable.get(j, i));
			}

			constraint_row.sortScope();
			constraint_column.sortScope();

			//System.out.println(countConstraint++ + ". addConstraint row " + (i + 1));
			//System.out.println(countConstraint++ + ". addConstraint column " + (i + 1));

			addConstraint(constraint_row);
			addConstraint(constraint_column);

		}

		//add constraint_square
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
			//	System.out.println(countConstraint++ + ". addConstraint square (" + i + "-" + j + ")");
				addConstraint(listVariable.constraint_square(i, j));
			}
		}

	}



}
