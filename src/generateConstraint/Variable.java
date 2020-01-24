package generateConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Variable {

    String id = null;
    List<Integer> domaine = new ArrayList<Integer>();
    int connectivite = 0;

    public Variable(int numero, List<Integer> domaine) {
        this.id = "X" + numero;
        this.domaine = domaine;
    }

    public Variable(int numero, int tailleMaxDomaine) {
        this.id = "X" + numero;

        int tailleDomaine = generateRandomFrom1ToMax(tailleMaxDomaine);
        this.domaine = new ArrayList<Integer>();
        for (int i = 1; i <= tailleMaxDomaine; i++) {
            domaine.add(i);
        }

        // on retire pour obtenir le nombre de couple souhaite
        while (domaine.size() > tailleDomaine) {
            int indexToRemove = generateRandomFrom1ToMax(domaine.size()) - 1;
            domaine.remove(indexToRemove);
        }

    }

    private int generateRandomFrom1ToMax(int max) {
        Random r = new Random();
        int min = 1;
        int result = r.nextInt(max - min + 1) + min;
        return result;
    }

    @Override
    public String toString() {
        return id;
    }


    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Variable v = (Variable) obj;
        if (v == null) {
            return false;
        }
        return v.getId().equals(id);
    }

}
