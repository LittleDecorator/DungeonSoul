package com.sample.box.entities;

import com.badlogic.gdx.utils.Array;
import com.sample.box.ui.entity.Item;

public interface Container {

    public Array<Item> getInventory();
    public void setInventory(Array<Item> items);

}
