/*
package com.sample.box.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Info {

    private Player player;

    private TextureRegion[] font;
    BitmapFont bitmapFontInfo;

    private String playerInfo;
    private String playerPos;
    private String playerForces;

    public Info(Player player) {
        this.player = player;

//        font = new TextureRegion[11];
        bitmapFontInfo = new BitmapFont();
    }

    public void render(SpriteBatch sb) {

        sb.begin();
        // draw point amount
//        drawString(sb, player.getNumCrystals() + " / " + player.getTotalCrystals(), 132, 211);
        drawString(sb);
        sb.end();

    }

    private void drawString(SpriteBatch sb*/
/*, String s, float x, float y*//*
) {
        */
/*for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '/') c = 10;
            else if(c >= '0' && c <= '9') c -= '0';
            else continue;
            sb.draw(font[c], x + i * 9, y);
        }*//*

        bitmapFontInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bitmapFontInfo.draw(sb, playerInfo ,15,400);
        bitmapFontInfo.draw(sb, playerPos ,15,385);
        bitmapFontInfo.draw(sb, playerForces ,15,370);
    }

    public void setPlayerInfo(String playerInfo) {
        this.playerInfo = playerInfo;
    }

    public void setPlayerPos(String playerPos) {
        this.playerPos = playerPos;
    }

    public void setPlayerForces(String playerForces) {
        this.playerForces = playerForces;
    }
}
*/
