package model.SodokuApp;

import engine.csp.Assignment;
import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SodokuConstraint implements Constraint {

	public final List<Variable>	scope;

	public SodokuConstraint() {
		scope = new ArrayList<>();
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {

		//System.out.println("\ncall isSatisfiedWith : ");
		boolean sleep = false;
		Set<Integer> removed = new HashSet<Integer>(9);
		removed.add(1);
		removed.add(2);
		removed.add(3);
		removed.add(4);
		removed.add(5);
		removed.add(6);
		removed.add(7);
		removed.add(8);
		removed.add(9);

		for (int i = 0; i < scope.size(); i++) {
			Variable variable = scope.get(i);
			Object intValue = assignment.getValue(variable);
			if (intValue == null) {
				//System.out.println("  get assignment of '" + variable.toString() + "'  is null ");
				return true;
			} else {

				if (iSodokuConstraint != null) {
					if (((SodokuVariable) variable).value != (Integer) intValue) {
						iSodokuConstraint.setVariable((SodokuVariable) variable, (Integer) intValue);
					}
				}
				//System.out.println(variable + " assign =  " + intValue);				
				if (!removed.remove(intValue)) { return false; }
			}
		}

		return removed.size() == 0;
	}

	//for draw gui
	ISodokuListener	iSodokuConstraint;


	public void add(SodokuVariable sodokuVariable) {
		scope.add(sodokuVariable);

	}


	public void sortScope()
	{
		Collections.sort(scope,(Variable o1,Variable o2)->{
			SodokuVariable v1 = (SodokuVariable) o1;
			SodokuVariable v2 = (SodokuVariable) o2;
			return v2.value - v1.value;});
	}
}
