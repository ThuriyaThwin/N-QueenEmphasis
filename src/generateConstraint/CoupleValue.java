package generateConstraint;

public class CoupleValue {

    int value1;
    int value2;

    public CoupleValue(int valeur1, int valeur2) {
        this.value1 = valeur1;
        this.value2 = valeur2;
    }

    public int getValeur1() {
        return value1;
    }

    public int getValeur2() {
        return value2;
    }

    @Override
    public String toString() {
        return "(" + value1 + "," + value2 + ")";
    }
}
