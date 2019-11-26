package Backjumping.prosser;

import Backjumping.csp.Problem;
import Backjumping.csp.ValSet;

import java.util.ArrayList;

public class CBJ extends Bcssp {

    private ValSet conf_set[];
    private ValSet current_domain[];
    public static final StringBuilder stringBuilder=new StringBuilder();
    public static final ArrayList<String> a=new ArrayList<>();
    public static final ArrayList variable=new ArrayList();
    public String get()
    {
        return stringBuilder.toString();
    }
    public CBJ(Problem problem) {
        super(problem);
        current_domain = new ValSet[n];
        v = new int[n];
        conf_set = new ValSet[n];
        for (int i = 0; i < n; i++) {
            conf_set[i] = new ValSet(n);
            current_domain[i] = new ValSet(d);
            current_domain[i].fill();
        }
    }



        public int label(int i) {
            consistant = false;
       // System.out.println("### Queen Piece "+(i+1));
            variable.add(i);
         stringBuilder.append("Q"+(i+1)+"\n");

        int d_index = 0;
            while ((d_index < d) && (!consistant)) {

                if (!current_domain[i].isMember(d_index)) {
                    d_index++;
                    continue;
                }
                v[i] = d_index;
                //System.out.println("The Queen Movement :"+ (v[i]+1));
                stringBuilder.append("Q"+(i+1)+"="+ (v[i]+1)+"\n");
              //  a.add(i+" "+v[i]);
                assignments++;
                consistant = true;

                int h;

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
           // System.out.println("The placement :"+(v[i]+1)+"\n");
            /*a.append("\n");*/
            a.add(i+" "+v[i]);
            if (consistant)
                return (i + 1);
            else
                return (i);
    }


    @Override
    public int unlabel(int i) {

        int h;
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
