package com.rafaelmallare.terraphage;

import java.util.HashMap;

/**
 * Created by Rj on 10/19/2016.
 */

public class Gear {
    public Gear(){
        mName = "Gear";
        mSilCost = 0;
    }

    public Gear(String name, int cost, String type){
        mName = name;
        mSilCost = cost;
        mType = type;

        mStats = new HashMap<>();
    }

    public Gear(String name, int cost, String type, HashMap<String, Integer> stats){
        mName = name;
        mSilCost = cost;
        mType = type;

        mStats = stats;
    }

    private String mName;
    private int mSilCost;
    private String mType;

    private HashMap<String, Integer> mStats;

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = mName;
    }

    public int getSilCost() {
        return mSilCost;
    }

    public void setSilCost(int SilCost) {
        this.mSilCost = mSilCost;
    }

    public String getType() {
        return mType;
    }

    public void setType(String Type) {
        this.mType = mType;
    }

    public HashMap<String, Integer> getStatMap(){
        return mStats;
    }

    public int getStat(String statName){
        int result = 0;
        if(mStats.containsKey(statName)){
            result = mStats.get(statName);
        }
        return result;
    }

    public void setStat(String statName, int statValue){
        mStats.put(statName, statValue);
    }

    public void setMultiStat(HashMap<String, Integer> stats){
        mStats.putAll(stats);
    }
}
