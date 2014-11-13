package com.sample.box.helpers;

import com.badlogic.gdx.Screen;

public class ScreenHelper {

    private static Screen inventory;
    private static Screen menu;

    public static Screen getInventory() {
        return inventory;
    }

    public static void setInventory(Screen inventory) {
        ScreenHelper.inventory = inventory;
    }

    public static Screen getMenu() {
        return menu;
    }

    public static void setMenu(Screen menu) {
        ScreenHelper.menu = menu;
    }
}
