package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sample.box.character.Character;
import com.sample.box.helpers.GameHelper;

public class HUD {

    private Character player;

    private TextureRegion lifeFull;
//    private TextureRegion lifeHalf;
//    private TextureRegion lifeEmpty;

    public HUD(Character player) {

        this.player = player;

        Texture tex = GameHelper.getGame().getResource().getTexture("life");

        lifeFull = new TextureRegion(tex, 0, 0, 16 , 20);
//        lifeHalf = new TextureRegion(tex, 17, 0, 16 , 20);
//        lifeEmpty = new TextureRegion(tex, 33, 0, 16 , 20);

    }

    public void render(SpriteBatch sb) {
        sb.begin();
        // draw container
        for(int i =0; i < player.getLifeContainersCou(); i++){
            sb.draw(lifeFull, 16 + ((16 * i)+2), 450);
        }
        sb.end();
    }

}
