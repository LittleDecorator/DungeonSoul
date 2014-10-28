package com.sample.box.handlers;

public class GameInput {

    public static boolean[] KEYS = new boolean[3];

    //array positions is a key num
    //position value is a button marker (true = pressed, false = released)
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int FLAME = 2;

    public static void setKey(int i,boolean val){
        KEYS[i] = val;
    }

    public static boolean getKey(int i) {
        return KEYS[i];
    }

    public static void mark4skip(int i){
        KEYS[i] = false;
    }
}
