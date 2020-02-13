

import java.util.ArrayList;
import java.util.List;

import engine.csp.Assignment;
import engine.csp.Variable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssignmentTest {
	private static final Variable X = new Variable("x");
	private static final Variable Y = new Variable("y");

	private List<Variable> variables;
	private Assignment assignment;

	@Before
	public void setUp() {
		variables = new ArrayList<Variable>();
		variables.add(X);
		variables.add(Y);
		assignment = new Assignment();
	}

	@Test
	public void testAssignmentCompletion() {
		Assert.assertFalse(assignment.isComplete(variables));
		assignment.add(X, "Thuriya");
		Assert.assertFalse(assignment.isComplete(variables));
		assignment.add(Y, "Thwin");
		Assert.assertTrue(assignment.isComplete(variables));
		assignment.remove(X);
		Assert.assertFalse(assignment.isComplete(variables));
	}


}