package com.rafaelmallare.terraphage;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.rafaelmallare.terraphage.AttributeType.ARM;
import static com.rafaelmallare.terraphage.AttributeType.ATK;
import static com.rafaelmallare.terraphage.AttributeType.DEF;
import static com.rafaelmallare.terraphage.AttributeType.HP;
import static com.rafaelmallare.terraphage.AttributeType.INIT;
import static com.rafaelmallare.terraphage.AttributeType.MDMG;
import static com.rafaelmallare.terraphage.AttributeType.RDMG;
import static com.rafaelmallare.terraphage.AttributeType.REGEN;
import static com.rafaelmallare.terraphage.AttributeType.SPD;

/**
 * Created by Rj on 10/19/2016.
 */
public class Character {
    private static Character ourInstance = new Character();

    public static Character getInstance() {
        return ourInstance;
    }

    private Character() {
        equippedWeapon = null;

        mStatList = new ArrayList<>();
        mStatList.add(mStr);

        mAttributeList = new ArrayList<>();
        mAttributeList.add(new Attribute(HP));
        mAttributeList.add(new Attribute(REGEN));
        mAttributeList.add(new Attribute(INIT));
        mAttributeList.add(new Attribute(SPD));
        mAttributeList.add(new Attribute(DEF));
        mAttributeList.add(new Attribute(ATK));
        mAttributeList.add(new Attribute(MDMG));
        mAttributeList.add(new Attribute(RDMG));
        mAttributeList.add(new Attribute(ARM));
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

    private Weapon equippedWeapon;

    private ArrayList<Integer> mStatList;
    private ArrayList<Attribute> mAttributeList;
    private ArrayList<Gear> mInventoryList;

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

    public Attribute getAttribute(AttributeType attributeType){
        return mAttributeList.get(attributeType.getVal());
    }

    public void addGear(Gear gear){
        mInventoryList.add(gear);
    }

    public void removeGear(Gear gear){
        mInventoryList.remove(gear);
    }

    public void equipWeapon(Weapon weapon){
        equippedWeapon = weapon;
        for(Attribute attribute : mAttributeList){
            attribute.addModifier(weapon);
        }
    }

    public void unequipWeapon(Weapon weapon){
        equippedWeapon = null;
        for(Attribute attribute : mAttributeList){
            attribute.removeModifier(weapon);
        }
    }

    /**********Attribute Class**********/
    private class Attribute {
        public Attribute(AttributeType attributeType){
            mType = attributeType;
            mBaseValue = 0;
            mTotalValue = 0;
        }

        public Attribute(AttributeType attributeType, int baseValue){
            mType = attributeType;
            mBaseValue = baseValue;
            mTotalValue = mBaseValue;
        }

        private AttributeType mType;
        private int mBaseValue;
        private int mTotalValue;

        private HashMap<String, Integer> mModifiers;

        public int getBaseValue(){
            return updateBaseValue();
        }

        public int getTotalValue(){
            return updateTotalValue();
        }

        public HashMap<String, Integer> getModifiers(){
            return mModifiers;
        }

        private int updateTotalValue(){
            mTotalValue = updateBaseValue();

            for(Map.Entry<String, Integer> modifier : mModifiers.entrySet()){
                mTotalValue += modifier.getValue();
            }

            return mTotalValue;
        }

        public int updateBaseValue(){
            switch(mType){
                case HP:    mBaseValue = mCon + mStr + 10;
                    break;
                case REGEN: mBaseValue = mCon / 2;
                    break;
                case INIT:  mBaseValue = mDex + mPer;
                    break;
                case SPD:   mBaseValue = (mDex / 2) + 3;
                    break;

                case DEF:   mBaseValue = mDex /*+ Evasion skill*/;
                    break;
                case ATK:   mBaseValue = mPer /*+ Weapon skill*/;
                    break;
                case MDMG:  mBaseValue = mStr;
                    break;
                case RDMG:  //No action - RDMG depends only on ranged weapon damage
                case ARM:   //No action - ARM depends only on gear ARM atttribute
                default:    break;
            }

            return mBaseValue;
        }

        public void addModifier(Gear gear){
            for(Map.Entry<AttributeType, Integer> modifier : gear.getModifierMap().entrySet()){
                if(modifier.getKey() == mType){
                    mModifiers.put(gear.getName(), modifier.getValue());
                }
            }

            updateTotalValue();
        }

        public void removeModifier(Gear gear){
            String gearName = gear.getName();
            if(mModifiers.containsKey(gearName)){
                mModifiers.remove(gearName);
            }

            updateTotalValue();
        }
    }
}
