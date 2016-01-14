package com.nofferx.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lorenzo on 12-1-16.
 */
public class OrganizeDayOutSubject implements ISubject
{
    private IObserver ob;
    private ArrayList<HashMap<String,String>> historyList = new ArrayList<>();

    public ArrayList<HashMap<String,String>> getHistoryList(){
        return this.historyList;
    }

    @Override
    public void attach(IObserver ob) {
        this.ob = ob;
    }

    @Override
    public void setState(ArrayList<HashMap<String,String>> xmlList) {
        this.historyList = xmlList;
        this.notifyObserver();
    }

    @Override
    public void notifyObserver() {
        this.ob.update();
    }
}
