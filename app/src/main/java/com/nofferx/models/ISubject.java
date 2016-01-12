package com.nofferx.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lorenzo on 12-1-16.
 */
public interface ISubject {
    public void attach(IObserver i);
    public void setState(ArrayList<ArrayList<HashMap<String,String>>> xmlList);
    public void notifyObserver();
}
