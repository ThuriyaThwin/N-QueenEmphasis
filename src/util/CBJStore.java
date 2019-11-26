package util;

import java.util.ArrayList;

public class CBJStore {
    public static final ArrayList a=new ArrayList<>();
    public CBJStore(String value)
    {
        a.add(value);
    }

    public CBJStore()
    {

    }

    public void addResult(String value)
    {
        a.add(value);
    }

    public ArrayList getResult()
    {
        return a;
    }
}
