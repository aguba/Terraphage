package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 3/10/2017.
 */

public enum AttributeType {
    HP(0),
    REGEN(1),
    INIT(2),
    SPD(3),
    DEF(4),
    ATK(5),
    MDMG(6),
    RDMG(7),
    ARM(8);

    private int val;

    private AttributeType(int value){
        val = value;
    }

    public int value(){
        return val;
    }
}
