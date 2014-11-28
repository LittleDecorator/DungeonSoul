package com.sample.box.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.entities.Sprites;

public class SpriteHelper {

    public static void renderTexture(SpriteBatch sb, Texture tr, float x, float y){
        sb.begin();
        sb.draw(tr,x,y);
        sb.end();
    }

    public static void loadAllTextures(){
        GameHelper.getGame().getResource().loadTexture(Sprites.HAND,"hand");
    }

}
