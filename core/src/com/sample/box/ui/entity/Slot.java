package com.sample.box.ui.entity;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.listeners.SlotListener;

public class Slot extends Container<ImageActor>{

    //element in slot
    private Item item;

    //amount items in slot
    private int amount;

    //slot listeners (add item to slot, remove item)
    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    //constructor
    public Slot(Item item, int amount) {
        super(new ImageActor());
        setBackground(new TextureRegionDrawable(TextureHelper.getCell()));
        this.item = item;
        this.amount = amount;
        getActor().setSlot(this);
    }

    public Slot(){
        super(new ImageActor());
        setBackground(new TextureRegionDrawable(TextureHelper.getCell()));
        this.item = null;
        this.amount = 0;
        getActor().setSlot(this);
    }

    //flag that slot is empty
    public boolean isEmpty() {
        return item == null || amount <= 0;
    }

    //add items to slot
    public boolean add(Item item, int amount) {
        //if items equals then add and sum amount
        if (this.item == item || this.item == null) {
            this.item = item;
            this.amount += amount;
            notifyListeners();              //notify listeners
            return true;
        }
        return false;
    }

    //take item with amount
    public boolean take(int amount) {
        // if items more then take then minus
        if (this.amount >= amount) {
            this.amount -= amount;
            //if take all, then remove item
            if (this.amount == 0) {
                item = null;
            }
            notifyListeners();          //notify listeners
            return true;
        }
        return false;
    }

    //add listener to listeners array
    public void addListener(SlotListener slotListener) {
        slotListeners.add(slotListener);
    }

    //remove listener from array
    public void removeListener(SlotListener slotListener) {
        slotListeners.removeValue(slotListener, true);
    }

    //notify
    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setItem(Item item) {
        this.item = item;
        getActor().setImage(item.getImage());
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Slot[" + item + ":" + amount + "]";
    }

    public void clearSlot(){
        getActor().clear();
    }
}
