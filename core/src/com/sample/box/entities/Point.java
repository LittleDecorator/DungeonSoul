package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.Game;

public class Point extends B2DSprite{

    public Point(Body body) {
        super(body);
        Texture tex = Game.res.getTexture("point");
        TextureRegion[] sprites = TextureRegion.split(tex,16,16)[0];
        setAnimation(sprites,1/12f);

        setAnimation(sprites,1/12f);
    }


}
