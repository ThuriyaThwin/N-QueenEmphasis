package util;

import java.util.Hashtable;
import java.util.TreeMap;

/**
 * Stores key-value pairs for efficiency analysis.
 */

public class Metrics {
    private Hashtable<String, String> hash;

    public Metrics() {
        this.hash = new Hashtable<String, String>();
    }

    public void set(String name, int i) {
        hash.put(name, Integer.toString(i));
    }

    public String get(String name) {
        return hash.get(name);
    }

    /**
     * Sorts the key-value pairs by key names and formats them as equations.
     */

    public String toString() {
        TreeMap<String, String> map = new TreeMap<String, String>(hash);
        return map.toString();
    }


}
