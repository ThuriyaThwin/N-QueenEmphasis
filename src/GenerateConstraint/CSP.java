package GenerateConstraint;

import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CSP {

	List<Variable> lstVariable = null;
	StringBuilder stringBuilder=new StringBuilder();
	int nbVariables;


	public CSP(int n) throws Exception{
		FileWriter fileWriter = new FileWriter("./constraint/"+100+".txt");
		this.nbVariables = n;

		List<Integer> domain = new ArrayList<Integer>();
		for (int i = 1; i <= nbVariables; i++) {
			domain.add(i);
		}

		this.lstVariable = new ArrayList<Variable>();
		for (int i = 1; i <= nbVariables; i++) {
			lstVariable.add(new Variable(i, domain));
		}

		List<CoupleValue> lstCouple = new ArrayList<CoupleValue>();
		for (int i = 1; i <= nbVariables; i++) {
			for (int j = 1; j <= nbVariables; j++) {
				lstCouple.add(new CoupleValue(i, j));
			}
		}


		for (int i = 1; i <= nbVariables; i++) {
			for (int j = i + 1; j <= nbVariables; j++) {
				Variable var1 = lstVariable.get(i - 1);
				Variable var2 = lstVariable.get(j - 1);
				Constraint constraint = new Constraint(var1, var2, -1);
				List<CoupleValue> lstCoupleTmp = new ArrayList<CoupleValue>(lstCouple);
				for (int d = 1; d <= this.nbVariables; d++) {
					for (int k = lstCoupleTmp.size() - 1; k >= 0; k--) {
						CoupleValue tmpCV = lstCoupleTmp.get(k);
						int diffIAndJ = j - i;
						if (tmpCV.getValeur1() == tmpCV.getValeur2()
								|| tmpCV.getValeur2() + diffIAndJ <= this.nbVariables
										&& tmpCV.getValeur1() == tmpCV.getValeur2() + diffIAndJ
								|| tmpCV.getValeur2() - diffIAndJ >= 1
										&& tmpCV.getValeur1() == tmpCV.getValeur2() - diffIAndJ) {
							lstCoupleTmp.remove(k);
						}
					}
				}
				constraint.setLstCouple(new ArrayList<CoupleValue>(lstCoupleTmp));
				fileWriter.write(constraint.toString());
			}
		}

	}


	@Override
	public String toString() {
		return  "Constraint ("+ ") =" + "\n" + stringBuilder.toString();
	}

	public static void main(String[] args)throws Exception {
		int n=100;
		CSP csp = new CSP(n);
	}


}
