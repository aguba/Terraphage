package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 3/12/2017.
 */

public enum StatType {
    CON("PHYS"),
    STR("PHYS"),
    DEX("PHYS"),
    PER("MENT"),
    CHR("MENT"),
    INT("MENT");

    private String mCategory;

    private StatType(String category){
        mCategory = category;
    }

    public String getCategory(){
        return mCategory;
    }
}
