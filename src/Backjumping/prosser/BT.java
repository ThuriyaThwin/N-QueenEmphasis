package Backjumping.prosser;

import Backjumping.csp.Problem;
import Backjumping.csp.ValSet;

public class BT extends Bcssp {
    private ValSet current_domain[];

    public BT(Problem problem) {
        super(problem);
        current_domain = new ValSet[n];
        v = new int[n];
        for (int i = 0; i < n; i++) {
            current_domain[i] = new ValSet(d);
            current_domain[i].fill();
        }
    }


    public int label(int i) {
        System.out.println("label " + i);
        consistant = false;

        int d_index = 0;
        while ((d_index < d) && (!consistant)) {

            if (!current_domain[i].isMember(d_index)) {
                d_index++;
                continue;
            }

            v[i] = d_index;


            assignments++;
            consistant = true;

            int h;

            for (h = 0; (h < i) && consistant; h++) {//The problem it always false
                consistant = problem.check(i, v[i], h, v[h]);
            }

            if (!consistant) {
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
    public int unlabel(int i) {

        int h;
        h = i - 1;
        if (h == -1) {
            return h;
        }
        current_domain[i].fill();
        current_domain[h].remove(v[h]);

        consistant = (!current_domain[h].isEmpty());
        return h;
    }

}
