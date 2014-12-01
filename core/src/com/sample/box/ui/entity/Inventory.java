package com.sample.box.ui.entity;

import com.badlogic.gdx.utils.Array;

//inventory it's simply slot array
public class Inventory {

    private Array<Slot> stashSlots;
    private Array<Slot> quiverSlots;
    private Array<Slot> weaponSlots;
    private Array<Slot> itemSlots;

    //constructor
    public Inventory() {
        stashSlots = new Array<Slot>(20);
        quiverSlots = new Array<Slot>(3);
        weaponSlots = new Array<Slot>(3);
        itemSlots = new Array<Slot>(3);

    }

    //add item to inventory (from loot dialog, trade, ground or dialog)
    public boolean store(Item item, int amount) {
        // first check for a slot with the same item type
        Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item, amount);
            return true;
        } else {
            // now check for an available empty slot
            Slot emptySlot = firstEmptySlot();
            if (emptySlot != null) {
                emptySlot.add(item, amount);
                return true;
            }
        }
        // no slot to add
        return false;
    }

    //find slot in stash with current item
    private Slot firstSlotWithItem(Item item) {
        for (Slot slot : stashSlots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }
        return null;
    }

    //find first empty slot
    private Slot firstEmptySlot() {
        for (Slot slot : stashSlots) {
            if (slot.getItem() == null) {
                return slot;
            }
        }
        return null;
    }

    public Array<Slot> getStashSlots() {
        return stashSlots;
    }

    public Array<Slot> getQuiverSlots() {
        return quiverSlots;
    }

    public Array<Slot> getWeaponSlots() {
        return weaponSlots;
    }

    public Array<Slot> getItemSlots() {
        return itemSlots;
    }
}
