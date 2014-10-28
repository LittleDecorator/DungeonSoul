package com.sample.box.character;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.entities.B2DSprite;
import com.sample.box.helpers.GameHelper;

public class Warrior extends B2DSprite implements Character{

    private int numCrystals;
    private int totalCrystals;
//    private RayHandler rayHandler;

    public Warrior(Body body) {
        super(body);
        Texture tex = GameHelper.getGame().getResource().getTexture("Player");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
//        animation.setFrames(sprites, 1.5f / 12f);

//        width = sprites[0].getRegionWidth();
//        height = sprites[0].getRegionHeight();
    }

    public int getNumCrystals() { return numCrystals; }

    public void collectCrystals(){ numCrystals++; }

    public void setTotalCrystals(int i){ totalCrystals =i; }

    public int getTotalCrystals() { return totalCrystals; }

    public void flameOn(RayHandler handler){
//        this.rayHandler = handler;
//        new PointLight(rayHandler, 4, new Color(1,1,1,1), 8f, 5f, 4f);
    }

    @Override
    public void flameOff(RayHandler handler) {

    }
}
