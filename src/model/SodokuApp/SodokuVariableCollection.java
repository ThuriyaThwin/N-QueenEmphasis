package model.SodokuApp;

import engine.csp.Variable;
import engine.csp.constraints.Constraint;
import util.XYLocation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SodokuVariableCollection extends ArrayList<Variable> {

	private static final long	serialVersionUID	= 1L;

	public SodokuVariableCollection(String data) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				add(new SodokuVariable(new XYLocation(i, j)));
			}
		}
		getSodokuData(data);
	}

	private void getSodokuData(String file_data) {

		try {
			FileInputStream fstream = new FileInputStream(file_data);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int row = 0;
			while ((strLine = br.readLine()) != null) {

				//System.out.println();
				//System.out.println(strLine);

				String line = strLine.trim().replace(" ", "");

				if (line.length() == 0)
					continue;
				for (int i = 0; i < line.length(); i++) {
					try {
						int word = Integer.parseInt(String.valueOf(line.subSequence(i, i + 1)));
						if (word != 0) {
							processCell(row, i, word);
						}
					} catch (Exception e) {

					}
				}
				row++;
			}

			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void processCell(int row, int column, int value) {
		//System.out.println("process input cell at( " + row + "-" + column + ") value =  " + value);
		SodokuVariable v = get(row, column);
		v.value = value;
		v.fixed = true;
	}

	public SodokuVariable get(int x, int y) {
		for (Variable v : this) {
			if (v instanceof SodokuVariable) {
				if ((((SodokuVariable) v).location.getXCoOrdinate() == x//
				) && (((SodokuVariable) v).location.getYCoOrdinate() == y))//
					return (SodokuVariable) v;
			}
		}
		return null;
	}

	public Constraint constraint_square(int x, int y) {
		SodokuConstraint constraint_square = new SodokuConstraint();
		int start_x = x * 3;
		int end_x = start_x + 3;

		int start_y = y * 3;
		int end_y = start_y + 3;

		for (int k1 = start_x; k1 < end_x; k1++) {
			for (int k2 = start_y; k2 < end_y; k2++) {
				SodokuVariable v = get(k1, k2);
				constraint_square.add(v);
			}
		}

		constraint_square.sortScope();

		return constraint_square;
	}

	public String getString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int dt = get(i, j).value;
				sb.append(dt == 0 ? "-" : dt);
				if (((j + 1) % 3) == 0)
					sb.append(" | ");
			}
			if (((i + 1) % 3) == 0)
				sb.append("\n----------------");
			sb.append("\n");
		}
		return sb.toString();
	}

	public String getHtml() {
		StringBuilder sb = new StringBuilder("<html><table border=\"0\">");
		for (int i = 0; i < 9; i++) {
			sb.append("<tr>");
			for (int j = 0; j < 9; j++) {
				SodokuVariable dt = get(i, j);
				sb.append("<td text-align: center " + drawColor(i, j) + " height=\"25\" width=\"25\" >" + (dt.value == 0 ? "" : "<font size=\"4\" " + (dt.fixed ? " color=\"yellow\">" : " color=\"white\">") + dt.value + "</font>") + "</td>");
			}
			sb.append("\n</tr>");
		}
		sb.append("</table></html>");
		return sb.toString();
	}

	private String drawColor(int i, int j) {
		i++;
		j++;

		if (i <= 3) {
			if (j <= 3) {
				return "bgcolor=#153450";
			} else if (j <= 6) {
				return "bgcolor=#613D2D";
			} else if (j <= 9) { return "bgcolor=#EB5E00"; }
		} else if (i <= 6) {
			if (j <= 3) {
				return "bgcolor=#EB5E00";
			} else if (j <= 6) {
				return "bgcolor=#153450";
			} else if (j <= 9) { return "bgcolor=#613D2D"; }
		} else if (i <= 9) {
			if (j <= 3) {
				return "bgcolor=#613D2D";
			} else if (j <= 6) {
				return "bgcolor=#EB5E00";
			} else if (j <= 9) { return "bgcolor=#153450"; }
		}
		return "";
	}
}