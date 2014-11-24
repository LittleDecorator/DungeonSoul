package com.sample.box.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sample.box.entities.PositionedFixture;
import com.sample.box.entities.Sprites;

import static com.sample.box.handlers.B2DVars.PPM;

public class DrawTexture {

    public static void drawTorch(SpriteBatch sb , PositionedFixture fixture){

        Texture texture = (Texture)fixture.getFixture().getUserData();
        float width = texture.getWidth();
        float height = texture.getHeight();
        Vector2 point = fixture.getPoint();

        //draw
        sb.begin();
        sb.draw(texture, point.x*PPM, point.y*PPM);
        sb.end();
    }

}
