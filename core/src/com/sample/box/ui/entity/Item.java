package com.sample.box.ui.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sample.box.entities.ItemType;

/* simply game item*/
public class Item {

    private String name;
    private String description;
    private TextureRegion image;
    private ItemType type;

    public Item(String name, String description, TextureRegion image, ItemType type) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
