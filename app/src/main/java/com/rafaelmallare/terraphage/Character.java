package com.rafaelmallare.terraphage;

/**
 * Created by Rj on 10/19/2016.
 */
public class Character {
    private static Character ourInstance = new Character();

    public static Character getInstance() {
        return ourInstance;
    }

    private Character() {
    }

    private String mName;
    private String mHomeland;
    private int mSil;
}
