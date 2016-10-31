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

    //void->int
    //Updates baseValue and returns the sum of baseValue and the sum of all modifier values
    public int getTotalValue(){
        this.update();
        int totalValue = mBaseValue;
        for(String statName : mModifiers.keySet()){
            totalValue += mModifiers.get(statName);
        }
        return totalValue;
    }

    //void->int
    //Returns baseValue
    public int getBaseValue(){
        return mBaseValue;
    }

    //int->void
    //Sets the value of baseValue to "value"
    private void setBaseValue(int value){
        mBaseValue = value;
    }

    //void->void
    //BaseValue is updated by pulling relevant stat, skill, and other values correspondng to the
    //attribute's name
    public void update(){
        switch (mName){
            /*****Base Attributes*****/
            case "HP":  setBaseValue(mCharacter.getCon() + mCharacter.getStr() + 10);
            case "REGEN":  setBaseValue(mCharacter.getCon() / 2);
            case "INIT": setBaseValue(mCharacter.getDex() + mCharacter.getPer());
            case "SPD": setBaseValue(mCharacter.getDex() / 2 + 3);

            /*****Combat and Gear Attributes*****/
            case "DEF": setBaseValue(mCharacter.getDex() /*+ Evasion skill*/);
            case "ATK": setBaseValue(mCharacter.getPer() /*+ Weapon skill*/);
            case "MDMG": setBaseValue(mCharacter.getStr());
            case "RDMG": //No action - RDMG depends only on ranged weapon damage
            case "ARM": //No action - ARM depends only on gear ARM atttribute
        }
    }

    //String*int->void
    //Adds a modifier to mModifiers given a modifier source name and a modifier value
    public void addModifier(String source, int value){
        mModifiers.put(source, value);
    }

    //String->void
    //Given a modifier source name, removes the corresponding modifier from mModifiers
    public void removeModifier(String source){
        mModifiers.remove(source);
    }

    //String->boolean
    //Returns true if mModifier's contains a modifier corresponding to the give gear's name, and
    //false otherwise
    public boolean containsModifier(String sourceGearName){
        return mModifiers.containsKey(sourceGearName);
    }
}
