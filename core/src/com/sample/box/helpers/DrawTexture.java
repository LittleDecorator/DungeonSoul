package com.sample.box.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.entities.Sprites;

public class DrawTexture {

    public static void drawTorch(SpriteBatch sb , float x, float y){
        Texture tx = GameHelper.getGame().getResource().getTexture("torch");
        if(tx==null){
            tx = new Texture(Gdx.files.internal(Sprites.TORCH));
            GameHelper.getGame().getResource().loadTexture(Sprites.TORCH,"torch");
        }

        //draw
        sb.begin();
        sb.draw(tx,x,y);
        sb.end();
    }

}
