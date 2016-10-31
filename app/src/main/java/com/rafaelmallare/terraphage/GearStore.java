package com.rafaelmallare.terraphage;

import java.util.HashMap;
import java.util.Objects;

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

        addGear("Revolver", 200, "Weapon", "ATK", -1, "DMG", 10);
        addGear("Flail", 10, "Weapon", "ATK", 3, "DMG", 4);
        addGear("Leather Armor", 10, "Armor", "ARM", 2);
        addGear("Medium Plate", 50, "Armor", "ARM", 5, "DEF", -3, "SPD", -1);
    }

    private HashMap<String, Gear> mGearInventory;

    private void addGear(String gearName, int gearSilCost, String gearType, Object... gearAttributeNamesAndValues){
        mGearInventory.put(gearName, new Gear(gearName, gearSilCost, gearType, gearAttributeNamesAndValues));
    }
}
