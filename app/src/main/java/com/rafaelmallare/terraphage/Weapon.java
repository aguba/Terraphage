package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 3/10/2017.
 */

public class Weapon extends Gear {
    public Weapon(String weaponName, int silValue, WeaponType weaponType, Object... modifierNamesAndValues){
        super(weaponName, silValue, modifierNamesAndValues);
        mType = weaponType;

        mMeleeDamage = 0;
        mRangedDamage = 0;
        mRange = 0;
        mMaxAmmo = 0;
        mCurrentAmmo = mMaxAmmo;
        mAOE = 0;
    }

    private WeaponType mType;
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
}
