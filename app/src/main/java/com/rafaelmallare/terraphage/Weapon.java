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

        mMeleeDamage = 0;
        mRangedDamage = 0;
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

    private int mMeleeDamage;
    private int mRangedDamage;
    private int mMaxAmmo;
    private int mCurrentAmmo;
    private int mRange;
    private int mAOE;

    public void setWeaponStats(int meleeDamage){
        mMeleeDamage = meleeDamage;
    }

    public void setWeaponStats(int rangedDamage, int range, int maxAmmo){
        mRangedDamage = rangedDamage;
        mRange = range;
        mMaxAmmo = maxAmmo;
        mCurrentAmmo = mMaxAmmo;
    }

    public void setWeaponStats(int rangedDamage, int range, int maxAmmo, int AOE){
        setWeaponStats(rangedDamage, range, maxAmmo);
        mAOE = AOE;
    }

    public void setWeaponStats(int meleeDamage, int rangedDamage, int maxAmmo, int range, int AOE){
        setWeaponStats(rangedDamage, range, maxAmmo, AOE);
        mMeleeDamage = meleeDamage;
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

    public int getMeleeDamage(){
        return mMeleeDamage;
    }

    public void setMeleeDamage(int damage){
        mMeleeDamage = damage;
    }

    public int getRangedDamage(){
        return mRangedDamage;
    }

    public void setRangedDamage(int damage){
        mRangedDamage = damage;
    }

    public int getMaxAmmo(){
        return mMaxAmmo;
    }

    public void setMaxAmmo(int maxAmmo){
        mMaxAmmo = maxAmmo;
    }

    public int getCurrentAmmo(){
        return mCurrentAmmo;
    }

    public boolean setCurrentAmmo(int currentAmmo){
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
