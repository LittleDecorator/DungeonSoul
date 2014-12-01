package com.sample.box.entities;

public enum ItemType {

    MAGIC_SCROLL("Magic scroll"),
    SCHEMA("Engineer schema"),
    WEAPON("Melee weapon"),
    WAND("Magic wand"),
    QUIVER("Arrows, Bolts and other"),
    RING("Ring"),
    AMULET("Amulet"),
    TATTOO("Tattoo"),
    IMPLANT("Implant"),
    SHIELD("Shield"),
    BAG("Store bag"),
    BOTTLE("Potion"),
    QUEST("Quest item"),
    OTHER("Other staff");

    private String desc;

    ItemType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
