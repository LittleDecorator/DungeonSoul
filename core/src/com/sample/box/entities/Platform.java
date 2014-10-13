package com.sample.box.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class Platform extends B2DSprite{

    public Platform(Body body) {
        super(body);
        /*Texture tex = Game.res.getTexture("point");
        TextureRegion[] sprites = TextureRegion.split(tex,16,16)[0];
        setAnimation(sprites,1/12f);*/
    }

}
