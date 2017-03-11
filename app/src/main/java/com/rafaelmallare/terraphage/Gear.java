package com.rafaelmallare.terraphage;

import java.util.HashMap;

/**
 * Created by Rj on 3/10/2017.
 */

public class Gear {
    public Gear(String gearName, int silValue, Object... modifierNamesAndValues){
        mName = gearName;
        mSilValue = silValue;

        mModifiers = Helper.mapModifiers(modifierNamesAndValues);
    }

    private String mName;
    private int mSilValue;
    private HashMap<AttributeType, Integer> mModifiers;

    public String getName(){
        return mName;
    }

    public int getSilValue(){
        return mSilValue;
    }

    public void setSilValue(int silValue){
        mSilValue = silValue;
    }

    public HashMap<AttributeType, Integer> getModifierMap(){
        return mModifiers;
    }
}
