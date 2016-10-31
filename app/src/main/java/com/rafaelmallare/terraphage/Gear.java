package com.rafaelmallare.terraphage;

import java.util.HashMap;
import java.util.Objects;

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

        mAttributes = new HashMap<>();
    }

    public Gear(String name, int cost, String type, Object... attributeNamesAndValues){
        mName = name;
        mSilCost = cost;
        mType = type;

        mAttributes = Helper.easyMap(attributeNamesAndValues);
    }

    private String mName;
    private int mSilCost;
    private String mType;

    private HashMap<String, Integer> mAttributes;

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
        return mAttributes;
    }

    public int getAttribute(String attributeName){
        int result = 0;
        if(mAttributes.containsKey(attributeName)){
            result = mAttributes.get(attributeName);
        }
        return result;
    }

    public void setStat(String statName, int statValue){
        mAttributes.put(statName, statValue);
    }

    public void setMultiStat(HashMap<String, Integer> stats){
        mAttributes.putAll(stats);
    }
}
