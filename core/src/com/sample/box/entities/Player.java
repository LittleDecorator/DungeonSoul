package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.Game;

public class Player extends B2DSprite {

    private int numCrystals;
    private int totalCrystals;

    public Player(Body body) {
        super(body);
        Texture tex = Game.res.getTexture("player");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
    }

    public int getNumCrystals() { return numCrystals; }

    public void collectCrystals(){ numCrystals++; }

    public void setTotalCrystals(int i){ totalCrystals =i; }

    public int getTotalCrystals() { return totalCrystals; }
}
