package com.sample.box.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sample.box.Game;

import java.util.*;

public class BackgroundHelper {

//    private TextureRegion image;
    private OrthographicCamera camera;
//    private float scale;

//    private float x;
//    private float y;
    private int numDrawX;
    private int numDrawY;

    private Map<String, TextureRegion> imgMap = new HashMap<String, TextureRegion>();

//    private float dx;
//    private float dy;
    private SpriteBatch sb;

    public BackgroundHelper(OrthographicCamera camera,SpriteBatch sb) {
//        this.image = image;
        this.camera = camera;
        this.sb = sb;
//        this.scale = scale;
//        numDrawX = Game.V_WIDTH / image.getRegionWidth() + 1;
//        numDrawY = Game.V_HEIGHT / image.getRegionHeight() + 1;
    }

    //draw texture only once in position
    public void drawOnce(TextureRegion image, float x, float y, float scale){

        //somehow get new pos
        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();
        sb.draw(image, px, py);
        sb.end();
    }

    //draw texture only once scale to fullscreen
    public void drawOnceFull(TextureRegion image, float x, float y){
        sb.begin();
        sb.draw(image, x, y, Game.V_WIDTH,Game.V_HEIGHT);
        sb.end();
    }

    //draw texture as line repeatedly with offset
    public void drawRow(TextureRegion image, float x, float y, float offset, float scale){

        //get image cou in x coordinate
        numDrawX = Game.V_WIDTH / image.getRegionWidth() + 1;

        //somehow get new pos
        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        for(int col = 0; col < numDrawX; col++) {
            sb.draw(image, px + (col + offset) * image.getRegionWidth(), py * image.getRegionHeight());
        }

        sb.end();
    }

    //draw texture as column repeatedly with offset
    public void drawColumn(TextureRegion image, float x, float y, float offset, float scale){

        //get image cou in y coordinate
        numDrawY = Game.V_HEIGHT / image.getRegionHeight() + 1;

        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        for(int row = 0; row < numDrawY; row++) {
            sb.draw(image, px * image.getRegionWidth(), py + (offset + row) * image.getRegionHeight());
        }

        sb.end();
    }

    //draw texture repeatedly in all directions
    public void drawAll(TextureRegion image, float x, float y, float offsetX, float offsetY, float scale){
        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {

                sb.draw(image, x + (col + offsetX) * image.getRegionWidth(), y + (offsetY + row) * image.getRegionHeight());
            }
        }

        sb.end();
    }

    /*private void draw(TextureRegion image, float x, float y, float scale){

    }*/

    public void addImg(String name, TextureRegion img){
        imgMap.put(name, img);
    }

    public Set<String> getImgNames(){
        return imgMap.keySet();
    }

    public TextureRegion getImgByNames(String name){
        return imgMap.get(name);
    }

}
