package com.rafaelmallare.terraphage;

import java.util.HashMap;

import static com.rafaelmallare.terraphage.AttributeType.ATK;
import static com.rafaelmallare.terraphage.AttributeType.SPD;
import static com.rafaelmallare.terraphage.WeaponType.Melee;
import static com.rafaelmallare.terraphage.WeaponType.Ranged;

/**
 * Created by Rj on 10/29/2016.
 */
public class GearStore {
    private static GearStore ourInstance = new GearStore();

    public static GearStore getInstance() {
        return ourInstance;
    }

    private GearStore() {
        mGearInventory = new HashMap<>();

        Weapon Revolver = new Weapon("Revolver", 200, Ranged, ATK, -1);
        Revolver.setWeaponStats(10, 10, 6);
        Weapon GreatSword = new Weapon("Great Sword", 15, Melee, ATK, -3);
        GreatSword.setWeaponStats(4);
        Weapon Flail = new Weapon("Flail", 10, Melee, ATK, -1);
        Flail.setWeaponStats(4);

        addWeapon(Revolver);
        addWeapon(GreatSword);
        addWeapon(Flail);
    }

    private HashMap<String, Gear> mGearInventory;

    private void addWeapon(Weapon weapon){
        mGearInventory.put(weapon.getName(), weapon);
    }

    public Weapon getWeapon(String weaponName){
        return (Weapon) mGearInventory.get(weaponName);
    }
}
