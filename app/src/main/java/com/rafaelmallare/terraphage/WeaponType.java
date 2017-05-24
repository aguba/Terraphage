package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 3/10/2017.
 */

public enum WeaponType {
    Melee("Melee"),
    Ranged("Ranged"),
    Thrown("Thrown");

    private String mName;

    private WeaponType(String name){
        mName = name;
    }

    public String toString(){
        return mName;
    }
}
