package engine.csp.constraints;

import engine.csp.Assignment;
import engine.csp.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 *  Implementation of PairConstraint constraint.
 * The constraint is satisfied if a value is matched twice in a given set.
 */
public class PairConstraint implements Constraint<Variable, Integer> {

	private List <Variable> variables;
	private String value;
	private List<Variable> scope;

	public PairConstraint(List <Variable> variables, String value) {
		this.variables = variables;
		scope = new ArrayList<>(variables.size());
		scope = variables;
		this.value = value;
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
			int count = 0;

			for (int i = 0; i < variables.size(); i++){
				Object value = assignment.getValue(variables.get(i));
				if(value != null){
					if(value == this.value){
						count++;
					}					
				}
			}
			if(count > 2){
				return false;
			}
		return true;
	}
}
