package com.sample.box.helpers;

import com.badlogic.gdx.physics.box2d.World;

public class StateHelper {

    private static World world;

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        StateHelper.world = world;
    }
}
