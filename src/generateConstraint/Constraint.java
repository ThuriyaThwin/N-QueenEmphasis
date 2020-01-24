package generateConstraint;

import java.util.ArrayList;
import java.util.List;

public class Constraint {

    Variable variable1;
    Variable variable2;

    List<CoupleValue> lstCouple;

    public Constraint(Variable variable1, Variable variable2, int connectivite) {
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.lstCouple = new ArrayList<CoupleValue>();
    }

    @Override
    public String toString() {
        return "\n" + "Constraint [(" + variable1 + ", " + variable2 + ") : " + lstCouple + "]";
    }


    public void setLstCouple(List<CoupleValue> lstCouple) {
        this.lstCouple = lstCouple;
    }


}
