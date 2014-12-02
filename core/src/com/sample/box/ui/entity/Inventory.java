package com.sample.box.ui.entity;

import com.badlogic.gdx.utils.Array;

//inventory it's simply slot array
public class Inventory {

    private Array<Slot> stashSlots;
    private Array<Slot> quiverSlots;
    private Array<Slot> weaponSlots;
    private Array<Slot> itemSlots;

    private Slot head = new Slot();
    private Slot amulet = new Slot();
    private Slot leftShoulder = new Slot();
    private Slot rightShoulder = new Slot();
    private Slot leftHand = new Slot();
    private Slot rightHand = new Slot();
    private Slot chest = new Slot();
    private Slot leftRing = new Slot();
    private Slot rightRing = new Slot();
    private Slot leftLeg = new Slot();
    private Slot leftAnkle = new Slot();
    private Slot rightLeg = new Slot();
    private Slot rightAnkle = new Slot();

//    private Slot shield = new Slot();
//    private Slot coat = new Slot();
//    private Slot belt = new Slot();

    //constructor
    public Inventory() {
        stashSlots = emptyArray(20);
        quiverSlots = emptyArray(3);
        weaponSlots = emptyArray(3);
        itemSlots = emptyArray(3);

    }

    private Array<Slot> emptyArray(int capacity){
        Array<Slot> sa = new Array<Slot>(capacity);
        for(int i=0 ; i<capacity; i++){
            sa.add(new Slot());
        }
        return sa;
    }

    //add item to inventory (from loot dialog, trade, ground or dialog)
    public boolean store(Item item, int amount) {
        // first check for a slot with the same item type
        Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.getActor().add(item, amount);
            return true;
        } else {
            // now check for an available empty slot
            Slot emptySlot = firstEmptySlot();
            if (emptySlot != null) {
                emptySlot.getActor().add(item, amount);
                return true;
            }
        }
        // no slot to add
        return false;
    }

    //find slot in stash with current item
    private Slot firstSlotWithItem(Item item) {
        for (Slot slot : stashSlots) {
            if (slot.getActor().getItem() == item) {
                return slot;
            }
        }
        return null;
    }

    //find first empty slot
    private Slot firstEmptySlot() {
        for (Slot slot : stashSlots) {
            if (slot.getActor().getItem() == null) {
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

    public Slot getHead() {
        return head;
    }

    public void setHead(Slot head) {
        this.head = head;
    }

    public Slot getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(Slot leftLeg) {
        this.leftLeg = leftLeg;
    }

    public Slot getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(Slot rightLeg) {
        this.rightLeg = rightLeg;
    }

    public Slot getRightRing() {
        return rightRing;
    }

    public void setRightRing(Slot rightRing) {
        this.rightRing = rightRing;
    }

    public Slot getLeftRing() {
        return leftRing;
    }

    public void setLeftRing(Slot leftRing) {
        this.leftRing = leftRing;
    }

    public Slot getAmulet() {
        return amulet;
    }

    public void setAmulet(Slot amulet) {
        this.amulet = amulet;
    }

    public Array<Item> getStashItems(){
        Array<Item> items = new Array<Item>();
        for(Slot slot: stashSlots){
            items.add(slot.getActor().getItem());
        }
        return items;
    }

    public Slot getLeftShoulder() {
        return leftShoulder;
    }

    public void setLeftShoulder(Slot leftShoulder) {
        this.leftShoulder = leftShoulder;
    }

    public Slot getRightShoulder() {
        return rightShoulder;
    }

    public void setRightShoulder(Slot rightShoulder) {
        this.rightShoulder = rightShoulder;
    }

    public Slot getChest() {
        return chest;
    }

    public void setChest(Slot chest) {
        this.chest = chest;
    }

    public Slot getLeftAnkle() {
        return leftAnkle;
    }

    public void setLeftAnkle(Slot leftAnkle) {
        this.leftAnkle = leftAnkle;
    }

    public Slot getRightAnkle() {
        return rightAnkle;
    }

    public void setRightAnkle(Slot rightAnkle) {
        this.rightAnkle = rightAnkle;
    }

    public Slot getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(Slot leftHand) {
        this.leftHand = leftHand;
    }

    public Slot getRightHand() {
        return rightHand;
    }

    public void setRightHand(Slot rightHand) {
        this.rightHand = rightHand;
    }
}
