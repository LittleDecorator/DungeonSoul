package com.sample.box.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sample.box.Game;

public class Background {

    private TextureRegion image;
    private OrthographicCamera gameCam;
    private float scale;

    private float x;
    private float y;
    private int numDrawX;
    private int numDrawY;

    public Background(TextureRegion image, OrthographicCamera gameCam, float scale) {
        this.image = image;
        this.gameCam = gameCam;
        this.scale = scale;
        System.out.println("Game.V_WIDTH -> "+ Game.V_WIDTH);
        System.out.println("image.getRegionWidth() -> "+image.getRegionWidth());
        System.out.println("Game.V_HEIGHT -> "+ Game.V_HEIGHT);
        System.out.println("image.getRegionHeight() -> "+image.getRegionHeight());
        System.out.println();
        numDrawX = Game.V_WIDTH / image.getRegionWidth() + 1;
        numDrawY = Game.V_HEIGHT / image.getRegionHeight() + 1;
//        numDrawX = 1;
//        numDrawY = 1;
    }

    public void render(SpriteBatch sb) {
        float x = ((this.x + gameCam.viewportWidth / 2 - gameCam.position.x) * scale) % image.getRegionWidth();
        float y = ((this.y + gameCam.viewportHeight / 2 - gameCam.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        int colOffset = x > 0 ? -1 : 0;
        int rowOffset = y > 0 ? -1 : 0;

        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {

                sb.draw(image, x + (col + colOffset) * image.getRegionWidth(), y + (rowOffset + row) * image.getRegionHeight());
//                sb.draw(image, 0, 0,Game.V_WIDTH,Game.V_HEIGHT);
            }
        }

        sb.end();

    }

//    private void

}
