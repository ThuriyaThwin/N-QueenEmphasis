package model;

import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.Variable;
import engine.csp.constraints.DisjuctiveConstraint;
import engine.csp.constraints.SmallerThanConstraint;
import engine.csp.domain.Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JobSchedule extends CSP<Variable,String>{
	static ArrayList<Object> times;

	public JobSchedule() {
		Variable AxleF = new Variable("AxleF");//0
		addVariable(AxleF);
		Variable AxleB = new Variable("AxleB");//1
		addVariable(AxleB);
		Variable WheelRF = new Variable("WheelRF");//2
		addVariable(WheelRF);
		Variable WheelLF = new Variable("WheelLF");//3
		addVariable(WheelLF);
		Variable WheelRB = new Variable("WheelRB");//4
		addVariable(WheelRB);
		Variable WheelLB = new Variable("WheelLB");//5
		addVariable(WheelLB);
		Variable NutsRF = new Variable("NutsRF");//6
		addVariable(NutsRF);
		Variable NutsLF = new Variable("NutsLF");//7
		addVariable(NutsLF);
		Variable NutsRB = new Variable("NutsRB");//8
		addVariable(NutsRB);
		Variable NutsLB = new Variable("NutsLB");//9
		addVariable(NutsLB);
		Variable CapRF = new Variable("CapRF");//10
		addVariable(CapRF);
		Variable CapLF = new Variable("CapLF");//11
		addVariable(CapLF);
		Variable CapRB = new Variable("CapRB");//12
		addVariable(CapRB);
		Variable CapLB = new Variable("CapLB");//13
		addVariable(CapLB);
		Variable Inspect = new Variable("Inspect");//14
		addVariable(Inspect);


		 times= new ArrayList<Object>();
		for(int i = 1; i < 28; i++) {
			times.add(i);
		}

		Domain timesDomain = new Domain(times);


		for (Variable var : getVariables())
			setDomain(var, timesDomain);

		addConstraint(new SmallerThanConstraint(10,AxleF, WheelRF));
		addConstraint(new SmallerThanConstraint(10,AxleB,WheelRB));
		addConstraint(new SmallerThanConstraint(10,AxleF,WheelLF));
		addConstraint(new SmallerThanConstraint(10,AxleB,WheelLB));
		addConstraint(new SmallerThanConstraint(1,WheelRF, NutsRF));
		addConstraint(new SmallerThanConstraint(1,WheelLF, NutsLF));
		addConstraint(new SmallerThanConstraint(1,WheelRB, NutsRB));
		addConstraint(new SmallerThanConstraint(1,WheelLB, NutsLB));
		addConstraint(new SmallerThanConstraint(1,WheelRF, NutsRF));
		addConstraint(new SmallerThanConstraint(2,NutsRF, CapRF));
		addConstraint(new SmallerThanConstraint(2,NutsLF, CapLF));
		addConstraint(new SmallerThanConstraint(2,NutsRB, CapRB));
		addConstraint(new SmallerThanConstraint(2,NutsLB, CapLB));
		addConstraint(new DisjuctiveConstraint(10,AxleF, AxleB));
		addConstraint(new SmallerThanConstraint(3,AxleF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,AxleB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,WheelRF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,WheelLF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,WheelRB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,WheelLB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,NutsRF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,NutsLF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,NutsRB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,NutsLB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,CapRF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,CapLF, Inspect ));
		addConstraint(new SmallerThanConstraint(3,CapRB, Inspect ));
		addConstraint(new SmallerThanConstraint(3,CapLB, Inspect ));

		
	}

	public static void main(String args[]){
		System.out.println("Job Schedule Problem");
		CSP csp = new JobSchedule();
		System.out.println("Variables   ="+csp.getVariables());
		System.out.print("Domains		= ["); times.forEach(i-> System.out.print(i+","));
		System.out.println("]\nConstraints ="+csp.getConstraints());
		System.out.println("Backtracking search solver");
		FlexibleBacktrackingSolver backtrackingSolver=new FlexibleBacktrackingSolver();
		long start = new Date().getTime();
		Optional result = backtrackingSolver.solve(csp);
		long end = new Date().getTime();
		System.out.format("time: %.3f secs\n", (end-start)/1000.0);
		System.out.println("result=" + System.lineSeparator() + result.get());
	}

}
