package util;

import java.util.ArrayList;

public class StoreResult {
    public static final ArrayList a = new ArrayList<>();

    public StoreResult(String value) {
        a.add(value);
    }

    public StoreResult() {

    }

    public void addResult(String value) {
        a.add(value);
    }

    public ArrayList getResult() {
        return a;
    }

}
