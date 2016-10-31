package com.rafaelmallare.terraphage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rj on 10/21/2016.
 */

public class Helper {
    public Helper(){}

    private static Character mCharacter;

    public static void setCharacter(Character character){
        mCharacter = character;
    }

    //Totally rad method from StackOverflow that creates a HashMap from any number of arguments
    public static HashMap<String, Integer> easyMap(Object... objects) {

        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "The array has to be of an even size - size is "
                            + objects.length);
        }

        HashMap<String, Integer> values = new HashMap<String, Integer>();

        for (int x = 0; x < objects.length; x+=2) {
            values.put((String) objects[x], (Integer) objects[x + 1]);
        }

        return values;

    }
}
