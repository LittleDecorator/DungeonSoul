package com.sample.box.ui.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sample.box.ui.entity.Item;
import com.sample.box.ui.entity.Slot;

/**
 * extends default Image
 * simple actor for slot
 */
public class ImageActor extends Image {

    //flag that actor is empty
    boolean empty = true;

    //element in actor
    private Item item;

    //amount items in actor
    private int amount;

    //constructor with item
    public ImageActor(Item item, int amount) {
        super(item.getImage());             //draw actor image
        empty = false;
        this.item = item;
        this.amount = amount;
    }

    //constructor that create empty actor (no image, item and amount)
    public ImageActor() {
        this.item = null;
        this.amount = 0;
    }

    //set new actor image
    @Override
    public void setDrawable(Drawable drawable) {
        super.setDrawable(drawable);
        if(drawable==null){
            empty = true;
        } else {
            empty = false;
        }
    }

    //check that actor is empty
    public boolean isEmpty() {
        return empty;
    }

    //remove actor image and set flag
    public void clear(){
        if(!empty){
            setDrawable(null);
            empty = true;
        }
    }

    //set actor image as item image
    public void setImageFromItem() {
        setDrawable(new TextureRegionDrawable(item.getImage()));
    }

    //add items to actor
    public boolean add(Item item, int amount) {
        //if items equals then add and sum amount
        if (this.item == item || this.item == null) {
            this.item = item;
            this.amount += amount;
            setImageFromItem();
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
            return true;
        }
        return false;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    //set item and use it's image
    public void setItem(Item item) {
        this.item = item;
        setImageFromItem();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
