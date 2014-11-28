package com.sample.box.ui.actor;

import static com.sample.box.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.sample.box.ui.Container;
import com.sample.box.ui.Slot;
import com.sample.box.ui.SlotSource;
import com.sample.box.ui.SlotTarget;
import com.sample.box.ui.handler.HideContainerListener;
import com.sample.box.ui.stage.InventoryScreen;
import com.sample.box.ui.stage.LootScreen;

public class LootWindow extends Window {

    Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));
    private Array<Slot> slots;

    public LootWindow(Skin skin) {
        super("Stash", skin);
        super.debug();
        // add an "X" button to the top right of the window, and make it hide the inventory
        /*TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HideContainerListener(this));
        getButtonTable().add(closeButton).height(getPadTop());*/
//        row().fill().expandX();

//        getButtonTable().add(addInventorySlotsTable());
    }

    /*private Table addInventorySlotsTable(){
        Table slotTable = new Table(skin);
        slotTable.debug();
        slotTable.setFillParent(false);
        slotTable.setPosition(50,50);

        *//*createSlots();

        row().fill().expandX();

        // run through all slots and create SlotActors for each
        int i = 0;
        for (Slot slot : this.slots) {
            SlotActor slotActor = new SlotActor(skin, slot);
            add(slotActor);
            i++;
            if (i % 10 == 0) row();
        }*//*

        TextField txtChatBar = new TextField("do a chat", skin);
        txtChatBar.setName("txtChatBar");

        // Populate the Chat Table
        slotTable.add(txtChatBar).expandX();
        slotTable.row();


        return slotTable;
    }*/

    /*private void createSlots(){
        slots = new Array<Slot>(20);
        for (int i = 0; i < 20; i++) {
            slots.add(new Slot(null, 0));
        }
    }*/

}
