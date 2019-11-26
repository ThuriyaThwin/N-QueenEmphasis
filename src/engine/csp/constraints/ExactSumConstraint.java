package engine.csp.constraints;

import engine.csp.Assignment;
import engine.csp.Variable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of ExactSumConstraint. The constraint is satisfied if the sum of the
 * elements in a given set is equal to a constant.
 */
public class ExactSumConstraint implements Constraint<Variable, Integer> {

	private ArrayList<Variable> variables;
	private int sum;
	private List<Variable> scope;

	public ExactSumConstraint(ArrayList<Variable> variables, int sum) {
		this.variables = variables;
		scope = new ArrayList<>(variables.size());
		scope = variables;
		this.sum = sum;
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
		if(assignment.isComplete(variables)){
			int partialSum = 0;
			Iterator<Variable> it = variables.iterator();
			while(it.hasNext()){
				int value = (Integer) assignment.getValue(it.next());
				partialSum = partialSum + value;
			}
			if(partialSum != sum){
				return false;
			}
		}
		return true;

	}


}
