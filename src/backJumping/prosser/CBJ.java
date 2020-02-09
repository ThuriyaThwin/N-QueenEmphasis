package backJumping.prosser;

import backJumping.csp.Problem;
import backJumping.csp.ValSet;
import engine.csp.Assignment;
import engine.csp.Variable;
import nqueens.NQueensCSP;

import java.util.ArrayList;

public class CBJ extends Bcssp {

    public static final ArrayList<String> arrayList = new ArrayList<>();
    private ValSet conf_set[];
    private ValSet current_domain[];

    public CBJ(Problem problem) {
        super(problem);
        current_domain = new ValSet[n];
        v = new Integer[n];
        NQueensCSP csp=new NQueensCSP(n);
        csp.getConstraints();
        conf_set = new ValSet[n];
        for (Integer i = 0; i < n; i++) {
            conf_set[i] = new ValSet(n);
            current_domain[i] = new ValSet(d);
            current_domain[i].fill();
        }
    }

    public Integer label(Integer i) {
        Assignment a=new Assignment();
        consistant = false;
        Integer d_index = 0;
        while ((d_index < d) && (!consistant)) {

            if (!current_domain[i].isMember(d_index)) {
                d_index++;
                continue;
            }
            Variable variable=new Variable(""+i);
            a.add(variable,d_index);
            v[i] = d_index;
            arrayList.add(i + " " + v[i]);
            assignments++;
            consistant = true;

            Integer h;

            for (h = 0; (h < i) && consistant; h++) {//The problem it always false

                consistant = problem.check(i, v[i], h, v[h]);
            }

            if (!consistant) {

                if (!conf_set[i].isMember(h - 1)) {
                    conf_set[i].add(h - 1);
                }
                current_domain[i].remove(v[i]);
            }

            d_index++;
        }
        if (consistant)
            return (i + 1);
        else
            return (i);
    }


    @Override
    public Integer unlabel(Integer i) {
        Integer h;
        h = conf_set[i].get_max();

        if (h == -1) {
            return h;
        }
        conf_set[h].union(conf_set[i]);
        conf_set[i].remove(h);
        for (int j = h + 1; j <= i; j++) {
            conf_set[j].clear();
            current_domain[j].fill();
        }
        current_domain[h].remove(v[h]);
        consistant = (!current_domain[h].isEmpty());
        return h;
    }


}
