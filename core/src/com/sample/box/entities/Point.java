package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;

import static com.sample.box.utils.Console.log;

public class Point extends B2DSprite{

    public Point(Body body) {
        super(body);
        InfoHelper.getInfo().storeElement(new DisplayElement(body));
        Texture tex = GameHelper.getGame().getResource().getTexture("point");
        TextureRegion[] sprites = TextureRegion.split(tex,16,16)[0];
        animation.setFrames(sprites,1/30f);

        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();

    }

    @Override
    public void render(SpriteBatch sb) {
//        log("Point animation pos: x-> "+body.getPosition().x +" ; y->"+ body.getPosition().y);
        super.render(sb);
    }
}
