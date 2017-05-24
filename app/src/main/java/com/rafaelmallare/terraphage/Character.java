package com.rafaelmallare.terraphage;

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
import static com.rafaelmallare.terraphage.StatType.CHR;
import static com.rafaelmallare.terraphage.StatType.CON;
import static com.rafaelmallare.terraphage.StatType.DEX;
import static com.rafaelmallare.terraphage.StatType.INT;
import static com.rafaelmallare.terraphage.StatType.PER;
import static com.rafaelmallare.terraphage.StatType.STR;

/**
 * Created by Rj on 10/19/2016.
 */
public class Character {
    private static Character ourInstance = new Character();

    public static Character getInstance() {
        return ourInstance;
    }

    private Character() {
        mUnarmed = new Weapon("Unarmed", 0, WeaponType.Melee);

        mName = "Default Name";
        mHomeland = "Default Homeland";
        mSil = 0;
        mExp = 0;

        mAnima = 0;

        mEquippedWeapon = null;
        mInventoryList = new ArrayList<>();
        mInventoryList.add(mUnarmed);

        mRefID = 1;

        mPhysicalStats = new StatGroup(CON, STR, DEX);
        mMentalStats = new StatGroup(PER, CHR, INT);

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

        equipWeapon(mUnarmed);

    }

    private String mName;
    private String mHomeland;
    private int mSil;
    private int mExp;

    private int mAnima;

    private Weapon mEquippedWeapon;
    private Weapon mUnarmed;

    private StatGroup mPhysicalStats;
    private StatGroup mMentalStats;
    private ArrayList<Attribute> mAttributeList;

    private ArrayList<Gear> mInventoryList;
    private int mRefID;

    //region -REGION: Standard getters and setters-
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

    public int getExp() {
        return mExp;
    }

    public void setExp(int mExp) {
        this.mExp = mExp;
    }

    public void setSil(int Sil) {
        this.mSil = mSil;
    }

    public int getAnima() {
        return mAnima;
    }

    public void setAnima(int Anima) {
        this.mAnima = mAnima;
    }

    public Weapon getEquippedWeapon(){
        return mEquippedWeapon;
    }

    public ArrayList<Gear> getInventory(){
        return mInventoryList;
    }

    public ArrayList<Weapon> getWeapons(){
        ArrayList<Weapon> weaponList = new ArrayList<>();
        for (int i = 0; i < mInventoryList.size(); i++){
            Gear gear = mInventoryList.get(i);
            if (gear instanceof Weapon){
                Weapon weapon = (Weapon) gear;
                weaponList.add(weapon);
            }
        }

        return weaponList;
    }
    //endregion

    public int getStat(StatType stat){
        int statVal = 0;

        if(stat.getCategory() == "PHYS"){
            statVal = mPhysicalStats.getStat(stat);
        } else if(stat.getCategory() == "MENT"){
            statVal = mMentalStats.getStat(stat);
        }

        return statVal;
    }

    public boolean increaseStat(StatType stat, int increaseBy){
        boolean successful = false;

        if(stat.getCategory() == "PHYS"){
            successful = mPhysicalStats.increaseStat(stat, increaseBy);
        } else if (stat.getCategory() == "MENT") {
            successful = mMentalStats.increaseStat(stat, increaseBy);
        }

        return successful;
    }

    public Attribute getAttribute(AttributeType attributeType){
        return mAttributeList.get(attributeType.value());
    }

    public void addGear(Gear gear){
        Gear newGear = gear;
        newGear.setRefID(mRefID);
        mRefID++;

        mInventoryList.add(newGear);
    }

    public boolean removeGear(Gear gear){
        boolean success = false;
        if(gear.getRefID() != 0) {
            mInventoryList.remove(gear);
            success = true;

            if(mInventoryList.size() == 0){
                mRefID = 1;
            }
        }
        return success;
    }

    public void equipWeapon(Weapon weapon){
        mEquippedWeapon = weapon;
        weapon.equip();
        for(Attribute attribute : mAttributeList){
            attribute.addModifier(weapon);
        }
    }

    public void unequipWeapon(Weapon weapon){
        mEquippedWeapon = mUnarmed;
        weapon.unEquip();
        for(Attribute attribute : mAttributeList){
            attribute.removeModifier(weapon);
        }
    }

    /**********Attribute Class**********/
    public class Attribute {
        public Attribute(AttributeType attributeType){
            mType = attributeType;
            mBaseValue = 0;
            mTotalValue = 0;
            mModifiers = new HashMap<>();
        }

        public Attribute(AttributeType attributeType, int baseValue){
            mType = attributeType;
            mBaseValue = baseValue;
            mTotalValue = mBaseValue;
            mModifiers = new HashMap<>();
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
                case HP:    mBaseValue = mPhysicalStats.getStat(CON) + mPhysicalStats.getStat(STR) + 10;
                    break;
                case REGEN: mBaseValue = mPhysicalStats.getStat(CON) / 2;
                    break;
                case INIT:  mBaseValue = mPhysicalStats.getStat(DEX) + mMentalStats.getStat(PER);
                    break;
                case SPD:   mBaseValue = (mPhysicalStats.getStat(DEX) / 2) + 3;
                    break;

                case DEF:   mBaseValue = mPhysicalStats.getStat(DEX) /*+ Evasion skill*/;
                    break;
                case ATK:   mBaseValue = mMentalStats.getStat(PER) /*+ Weapon skill*/;
                    break;
                case MDMG:  mBaseValue = mPhysicalStats.getStat(STR);
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

    /**********StatGroup Class**********/
    public class StatGroup {
        public StatGroup(StatType statOne, StatType statTwo, StatType statThree){
            mStatMap = new HashMap<>();

            mStatMap.put(statOne, 0);
            mStatMap.put(statTwo, 0);
            mStatMap.put(statThree, 0);
        }

        private HashMap<StatType, Integer> mStatMap;

        private boolean checkMaxDifference(StatType stat, int newValue){
            HashMap<StatType, Integer> tmpMap = new HashMap<>();
            tmpMap.putAll(mStatMap);

            tmpMap.put(stat, newValue);

            int max = 0;
            int min = 1000;
            for(Map.Entry<StatType, Integer> entry : tmpMap.entrySet()){
                int currentVal = entry.getValue();
                if(max < currentVal){
                    max = currentVal;
                }
                if(min > currentVal){
                    min = currentVal;
                }
            }

            if((max - min) > 4){
                return false;
            } else{
                return true;
            }
        }

        public int getStat(StatType stat){
            return mStatMap.get(stat);
        }

        public HashMap<StatType, Integer> getAllStats(){
            return mStatMap;
        }

        public boolean increaseStat(StatType stat, int increaseBy){
            int newValue = mStatMap.get(stat) + increaseBy;

            if(checkMaxDifference(stat, newValue)) {
                mStatMap.put(stat, newValue);
                return true;
            } else{
                return false;
            }
        }
    }
}
