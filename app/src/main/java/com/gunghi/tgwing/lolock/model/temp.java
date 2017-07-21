package com.gunghi.tgwing.lolock.model;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public class temp {
    private static final temp ourInstance = new temp();

    public static temp getInstance() {
        return ourInstance;
    }

    private temp() {
    }
}
