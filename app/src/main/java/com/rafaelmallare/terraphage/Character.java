package com.rafaelmallare.terraphage;

import java.util.HashMap;

/**
 * Created by Rj on 10/19/2016.
 */
public class Character {
    private static Character ourInstance = new Character();

    public static Character getInstance() {
        return ourInstance;
    }

    private Character() {

        helper = new Helper();
    }

    private String mName;
    private String mHomeland;
    private int mSil;

    private int mStr;
    private int mCon;
    private int mDex;
    private int mPer;
    private int mChr;
    private int mInt;
    private int mAnima;

    private HashMap<String, Attribute> mAttributes;

    private HashMap<String, Gear> mInventory;

    private Helper helper;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = mName;
    }

    public String getHomeland() {
        return mHomeland;
    }

    public void setHomeland(String Homeland) {
        this.mHomeland = mHomeland;
    }

    public int getSil() {
        return mSil;
    }

    public void setSil(int Sil) {
        this.mSil = mSil;
    }

    public int getStr() {
        return mStr;
    }

    public void setStr(int Str) {
        this.mStr = mStr;
    }

    public int getCon() {
        return mCon;
    }

    public void setCon(int Con) {
        this.mCon = mCon;
    }

    public int getDex() {
        return mDex;
    }

    public void setDex(int Dex) {
        this.mDex = mDex;
    }

    public int getPer() {
        return mPer;
    }

    public void setPer(int Per) {
        this.mPer = mPer;
    }

    public int getChr() {
        return mChr;
    }

    public void setChr(int Chr) {
        this.mChr = mChr;
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(int Int) {
        this.mInt = mInt;
    }

    public int getAnima() {
        return mAnima;
    }

    public void setAnima(int Anima) {
        this.mAnima = mAnima;
    }

    public void addGear(Gear gear){
        HashMap<String, Integer> modifierStats = gear.getStatMap();
        mInventory.put(gear.getName(), gear);
        for(String modifierName : modifierStats.keySet()){
            int modifierValue = modifierStats.get(modifierName);
            addModifier(modifierName, modifierValue, gear);
        }
    }

    /*****Helper Methods*****/
    void addModifier(String modifierName, int modifierValue, Gear sourceGear){
        if(modifierValue != 0){
            mAttributes.get(modifierName).addModifier(sourceGear.getName(), modifierValue);
        }
    }
}
