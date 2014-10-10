package com.sample.box.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HUD {

    private Player player;
    private TextureRegion[] blocks;

    public HUD(Player player) {
        this.player = player;
    }
}
