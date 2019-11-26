package model;


import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import engine.csp.constraints.NotEqualConstraint;
import engine.csp.constraints.PairsInTableOrNone;
import engine.csp.constraints.PairsNotSameTable;
import engine.csp.domain.Domain;
import engine.csp.inference.ForwardCheckingStrategy;

import java.util.*;


public class WeddingCSP extends CSP {
    Hashtable<String, List<Variable>> tables = new Hashtable<String, List<Variable>>();
    public List<Variable> chairs = new ArrayList<Variable>();


    // it is not defined for n-ary constraints
    @Override
    public Variable getNeighbor(Variable var, Constraint constraint) {
        List<Variable> scope = constraint.getScope();
        Variable selectedVar = null;
        if (scope.size() == 2) {
            if (var == scope.get(0))
                selectedVar = scope.get(1);
            else if (var == scope.get(1))
                selectedVar = scope.get(0);
        } else {
            // selects another right sibling. Starts again from 0 if
            // reaching the end of the scope.
            int k = 0;
            while (selectedVar == null && k < scope.size()) {
                Variable cvar = scope.get(k);
                if (cvar == var && k < scope.size() - 1) {
                    selectedVar = scope.get(k + 1);
                } else if (cvar == var && k == scope.size() - 1)
                    selectedVar = scope.get(0);
                else
                    k = k + 1;
            }
        }
        return selectedVar;
    }


    private void collectVariables() {
        for (List<Variable> al : tables.values())
            for (Variable v : al)
                addVariable(v);
    }

    public String prettyPrintAssignment(Assignment assignment) {
        if (assignment != null) {
            String result = "";
            for (String table : this.tables.keySet()) {
                List<Variable> chairs = tables.get(table);
                result = result + ("\nTable =" + table + "\n");
                for (Variable var : chairs) {
                    if (assignment.contains(var)) {
                        result = result + (var.getName() + " = " + assignment.getValue(var));
                        result = result + " , ";
                    } else
                        result = result + (var.getName() + " = null" + ",");
                }
                result = result + "\n";
            }
            return result;
        }
        return "not found";

    }


    /**
     * Defines the wedding tables
     */
    public WeddingCSP() {
        ArrayList<Variable> table1 = new ArrayList<Variable>();
        table1.add(new Variable("chair1-1"));
        table1.add(new Variable("chair2-1"));
        table1.add(new Variable("chair3-1"));
        table1.add(new Variable("chair4-1"));
        table1.add(new Variable("chair5-1"));

        ArrayList<Variable> table2 = new ArrayList<Variable>();
        table2.add(new Variable("chair1-2"));
        table2.add(new Variable("chair2-2"));
        table2.add(new Variable("chair3-2"));
        table2.add(new Variable("chair4-2"));
        table2.add(new Variable("chair5-2"));

        ArrayList<Variable> table3 = new ArrayList<Variable>();
        table3.add(new Variable("chair1-3"));
        table3.add(new Variable("chair2-3"));
        table3.add(new Variable("chair3-3"));
        table3.add(new Variable("chair4-3"));
        table3.add(new Variable("chair5-3"));

        tables.put("table1", table1);
        tables.put("table2", table2);
        tables.put("table3", table3);

        collectVariables();

        Domain people = new Domain(new Object[]{
                "m1", "m2", "m3", "m4", "m5", "m6", "m7",
                "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8"});

        List<Variable> vars = getVariables();

        for (Variable var : vars)
            setDomain(var, people);

        // every value can be assigned only once
        for (int k = 0; k < vars.size(); k++) {
            for (int j = k + 1; j < vars.size(); j++)
                addConstraint(new NotEqualConstraint(vars.get(k), vars.get(j)));
        }

        addConstraint(new PairsInTableOrNone("m7", "f7", tables.get("table1")));
        addConstraint(new PairsInTableOrNone("m7", "f7", tables.get("table2")));
        addConstraint(new PairsInTableOrNone("m7", "f7", tables.get("table3")));

        addConstraint(new PairsInTableOrNone("m2", "f2", tables.get("table1")));
        addConstraint(new PairsInTableOrNone("m2", "f2", tables.get("table2")));
        addConstraint(new PairsInTableOrNone("m2", "f2", tables.get("table3")));


        addConstraint(new PairsInTableOrNone("m3", "f3", tables.get("table1")));
        addConstraint(new PairsInTableOrNone("m3", "f3", tables.get("table2")));
        addConstraint(new PairsInTableOrNone("m3", "f3", tables.get("table3")));

        addConstraint(new PairsInTableOrNone("m4", "f4", tables.get("table1")));
        addConstraint(new PairsInTableOrNone("m4", "f4", tables.get("table2")));
        addConstraint(new PairsInTableOrNone("m4", "f4", tables.get("table3")));

        addConstraint(new PairsInTableOrNone("m5", "f5", tables.get("table1")));
        addConstraint(new PairsInTableOrNone("m5", "f5", tables.get("table2")));
        addConstraint(new PairsInTableOrNone("m5", "f5", tables.get("table3")));

        addConstraint(new PairsNotSameTable("f1", "f2", tables));
        addConstraint(new PairsNotSameTable("m2", "m3", tables));
        addConstraint(new PairsNotSameTable("m2", "m4", tables));
        addConstraint(new PairsNotSameTable("m2", "m5", tables));
    }


    public static void main(String[] args) {
        WeddingCSP csp = new WeddingCSP();
        FlexibleBacktrackingSolver solver = new FlexibleBacktrackingSolver();
        Date startTime = null;
        startTime = new Date();
        solver.set(new ForwardCheckingStrategy()).set(CspHeuristics.mrv());
        Optional<Assignment> sol = solver.solve(csp);
        System.out.println("Wedding Seating Arrangement (FC+MRV algorithm)");
        // a copy domains cannot be done in this case because of the cloning operation.
        // It cannot be serialized
        System.out.println(csp.prettyPrintAssignment(sol.get()));

        System.out.println("Time Required :" + ((new Date().getTime() - startTime.getTime()) / (1000f * 60f)) + " minutes \n");

    }

}