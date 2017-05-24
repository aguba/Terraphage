package com.rafaelmallare.terraphage;

import java.util.HashMap;

/**
 * Created by Rj on 3/10/2017.
 */

public class Gear {
    public Gear(String gearName, int silValue, Object... modifierNamesAndValues){
        mName = gearName;
        mSilValue = silValue;
        mEquipped = false;
        mRefID = 0;

        mModifiers = new HashMap<>();

        if(modifierNamesAndValues != null) {
            mModifiers.putAll(Helper.mapModifiers(modifierNamesAndValues));
        }
    }

    private String mName;
    private int mSilValue;
    private HashMap<AttributeType, Integer> mModifiers;
    private boolean mEquipped;
    private int mRefID;

    public String getName(){
        return mName;
    }

    public int getSilValue(){
        return mSilValue;
    }

    public void setSilValue(int silValue){
        mSilValue = silValue;
    }

    public int getModifier(AttributeType attributeType){
        int modifierValue = 0;
        if(mModifiers.containsKey(attributeType)){
            modifierValue = mModifiers.get(attributeType);
        }

        return modifierValue;
    }

    public HashMap<AttributeType, Integer> getModifierMap(){
        return mModifiers;
    }

    public boolean isEquipped(){
        return mEquipped;
    }

    public void equip(){
        mEquipped = true;
    }

    public void unEquip(){
        mEquipped = false;
    }

    public int getRefID(){
        return mRefID;
    }

    public void setRefID(int refID){
        mRefID = refID;
    }
}
