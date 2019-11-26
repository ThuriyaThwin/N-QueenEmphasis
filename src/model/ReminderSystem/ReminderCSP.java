package model.ReminderSystem;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.NotEqualConstraint;
import engine.csp.domain.Domain;

import java.util.ArrayList;
import java.util.List;

public class ReminderCSP extends CSP<Variable,String> {

    /**
     * Returns the reminder candidates from user input
     *
     * @return list of reminders
     */
    private static List<Variable> collectVariables(String[] candidate) {
        List<Variable> variables = new ArrayList<>();
        for (int i = 0; i < candidate.length ; i++) {
            variables.add(new Variable(candidate[i]));
        }
        return variables;
    }

    /**
     * Returns the remindee candidates from user input
     *
     * @return list of remindee
     */
    private static List<Domain> collectDomains(List<Object[]> domains) {
        List<Domain> domainList = new ArrayList<Domain>();
        for (Object[] domain : domains) {
            domainList.add(new Domain(domain));
        }
        return domainList;
    }



    public void addUniqueValueConstraint(Variable var) {
        for (Variable otherVar : getVariables()) {
            if (!var.equals(otherVar)) {
                addConstraint(new NotEqualConstraint(var, otherVar));
            }
        }
    }

    /**
     * Constructs a reminder system.
     */
    public ReminderCSP(String[] variables, List<Object[]> domains) {
        super(collectVariables(variables));
        List<Variable> reminders = getVariables();
        List<Domain> remindees = collectDomains(domains);

        for (int i = 0; i < reminders.size(); i++) {
            setDomain(reminders.get(i), remindees.get(i));
        }

        for (Variable reminder : reminders) {
            addUniqueValueConstraint(reminder);
        }
    }

}
