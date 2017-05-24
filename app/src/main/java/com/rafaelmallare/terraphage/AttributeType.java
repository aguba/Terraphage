package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 3/10/2017.
 */

public enum AttributeType {
    HP(0, "HP"),
    REGEN(1, "REGEN"),
    INIT(2, "INIT"),
    SPD(3, "SPD"),
    DEF(4, "DEF"),
    ATK(5, "ATK"),
    MDMG(6, "MDMG"),
    RDMG(7, "RDMG"),
    ARM(8, "ARM");

    private int mVal;
    private String mName;

    private AttributeType(int value, String name){
        mVal = value;
        mName = name;
    }

    public int value(){
        return mVal;
    }

    public String toString(){
        return mName;
    }
}
