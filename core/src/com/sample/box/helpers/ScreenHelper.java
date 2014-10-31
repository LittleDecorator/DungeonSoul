package com.sample.box.helpers;

import com.badlogic.gdx.Screen;

public class ScreenHelper {

    private static Screen inventory;

    public static Screen getInventory() {
        return inventory;
    }

    public static void setInventory(Screen inventory) {
        ScreenHelper.inventory = inventory;
    }
}
