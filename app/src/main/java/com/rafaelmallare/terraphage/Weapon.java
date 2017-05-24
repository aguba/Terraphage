package com.rafaelmallare.terraphage;

import static com.rafaelmallare.terraphage.Weapon.Rank.E;

/**
 * Created by Rj on 3/10/2017.
 */

public class Weapon extends Gear {
    public Weapon(String weaponName, int silValue, WeaponType weaponType, Object... modifierNamesAndValues){
        super(weaponName, silValue, modifierNamesAndValues);

        mType = weaponType;
        mRank = E;

        mRange = 0;
        mMaxAmmo = 0;
        mCurrentAmmo = mMaxAmmo;
        mAOE = 0;
    }

    public enum Rank{
        A("A"), B("B"), C("C"), D("D"), E("E");

        private String mValue;
        private Rank(String value){
            mValue = value;
        }

        public String value(){
            return mValue;
        }
    }

    private WeaponType mType;
    private Rank mRank;

    private int mMaxAmmo;
    private int mCurrentAmmo;
    private int mRange;
    private int mAOE;

    public void setWeaponStats(int ammoMax){
        mMaxAmmo = ammoMax;
        mCurrentAmmo = mMaxAmmo;
    }

    public void setWeaponStats(int ammoMax, Rank rank){
        setWeaponStats(ammoMax);
        mRank = rank;
    }

    public void setWeaponStats(int ammoMax, int range){
        mMaxAmmo = ammoMax;
        mCurrentAmmo = mMaxAmmo;

        mRange = range;
    }

    public void setWeaponStats(int ammoMax, int range, Rank rank){
        setWeaponStats(ammoMax, range);
        mRank = rank;
    }

    public void setWeaponStats(int ammoMax, int range, int AOE){
        setWeaponStats(ammoMax, range);
        mAOE = AOE;
    }

    public void setWeaponStats(int ammoMax, int range, int AOE, Rank rank){
        setWeaponStats(ammoMax, range, AOE);
        mRank = rank;
    }

    //region -REGION: Getters and setters for Type, Rank, Damage, Ammo, Range, and AOE-
    public WeaponType getWeaponType(){
        return mType;
    }

    public void setWeaponType(WeaponType weaponType){
        mType = weaponType;
    }

    public String getRank(){
        return mRank.value();
    }

    public void setRank(Rank rank){
        mRank = rank;
    }

    public int getAmmoMax(){
        return mMaxAmmo;
    }

    public void setAmmoMax(int maxAmmo){
        mMaxAmmo = maxAmmo;
    }

    public int getAmmoCurrent(){
        return mCurrentAmmo;
    }

    public boolean setAmmoCurrent(int currentAmmo){
        boolean success = false;
        if (currentAmmo <= mMaxAmmo){
            success = true;
            mCurrentAmmo = currentAmmo;
        } else{
            success = false;
        }
        return success;
    }

    public int getRange(){
        return mRange;
    }

    public void setRange(int range){
        mRange = range;
    }

    public int getAOE(){
        return mAOE;
    }

    public void setAOE(int AOE){
        mAOE = AOE;
    }
    //endregion
}
