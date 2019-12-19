package engine.csp.constraints;

import engine.csp.Assignment;
import engine.csp.Variable;

import java.util.ArrayList;
import java.util.List;

public class SmallerThanConstraint implements Constraint{
	private List<Variable> scope;
	@Override
	public String toString() {
		return "[" + a.getName()  + " + "+ time + " <= " + b.getName() + "]" + System.lineSeparator();
	}

	Variable a;
	Variable b;
	int time;

	public SmallerThanConstraint(int time , Variable a, Variable b) {
		this.time = time;
		this.a = a;
		this.b = b;
		scope = new ArrayList<Variable>(2);
		scope.add(a);
		scope.add(b);
	}




	@Override
	public List getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		Object aa = assignment.getValue(a);
		Object bb = assignment.getValue(b);
		boolean nullA = (aa == null);
		boolean nullB = (bb == null);
		if(nullA || nullB) {
			return true;
		}
		int firstTime = (int)aa;
		int secondTime =(int)bb;
		boolean result = (firstTime + time) <= (secondTime);
		return result;
	}
}
