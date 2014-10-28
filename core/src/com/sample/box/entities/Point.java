package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.Game;
import com.sample.box.helpers.GameHelper;

public class Point extends B2DSprite{

    public Point(Body body) {
        super(body);
        Texture tex = GameHelper.getGame().getResource().getTexture("point");
        TextureRegion[] sprites = TextureRegion.split(tex,16,16)[0];
        animation.setFrames(sprites,1/30f);

        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();

    }


}
