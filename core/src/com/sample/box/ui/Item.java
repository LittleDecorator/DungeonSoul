package com.sample.box.ui;

public enum Item {
    CRYSTAL_RED("redcrystal"),
    CRYSTAL_BLUE("bluecrystal"),
    CRYSTAL_GREEN("greencrystal"),
    CRYSTAL_YELLOW("yellowcrystal"),
    CRYSTAL_MAGENTA("magentacrystal"),
    CRYSTAL_CYAN("cyancrystal"),
    CRYSTAL_ORANGE("orangecrystal");

    private String textureRegion;

    private Item(String textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getTextureRegion() {
        return textureRegion;
    }
}
