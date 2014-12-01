package com.sample.box.ui.entity;

import com.badlogic.gdx.utils.Array;

/* class used for loot containers*/
public class Container {

    private Array<Slot> slots;


    //create slots array
    public Container() {
        slots = new Array<Slot>(20);
    }

    public Array<Slot> getSlots() {
        return slots;
    }
}
