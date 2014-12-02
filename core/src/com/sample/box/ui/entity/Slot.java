package com.sample.box.ui.entity;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.listeners.SlotListener;

public class Slot extends Container<ImageActor>{

    //constructor with item define
    public Slot(Item item, int amount) {
        this(new ImageActor(item,amount));
    }

    //constructor with empty item
    public Slot(){
        this(new ImageActor());
    }

    //default constructor
    private Slot(ImageActor actor){
        super(actor);
        setBackground(new TextureRegionDrawable(TextureHelper.getCell()),false);
        height(50);
        width(50);
    }

    //flag that slot is empty
    public boolean isEmpty() {
        return getActor().isEmpty();
    }

    //clear slot
    public void clearSlot(){
        getActor().clear();
    }
}
