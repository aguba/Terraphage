package com.rafaelmallare.terraphage;

import java.util.HashMap;

/**
 * Created by Rj on 10/19/2016.
 */

public class Attribute {
    public Attribute(){
        mName = "Attribute";
        mBaseValue = 0;
    }

    public Attribute(Character character, String name){
        mCharacter = character;
        mName = name;
        update();
    }

    private Character mCharacter;
    private String mName;
    private int mBaseValue;

    private HashMap<String, Integer> mModifiers;

    public int getBaseValue(){
        return mBaseValue;
    }

    private void setBaseValue(int value){
        mBaseValue = value;
    }

    public void update(){
        switch (mName){
            case "HP":  setBaseValue(mCharacter.getCon() + mCharacter.getStr() + 10);
            case "DEF": setBaseValue(mCharacter.getDex() /*+ Evasion skill*/);
            case "ATK": setBaseValue(mCharacter.getPer() /*+ Weapon skill*/);
            case  "REGEN":  setBaseValue(mCharacter.getCon() / 2);
        }
    }

    public void addModifier(String source, int value){
        mModifiers.put(source, value);
    }

    public void removeModifier(String source){
        mModifiers.remove(source);
    }
}
