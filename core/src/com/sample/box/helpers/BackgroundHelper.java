package com.sample.box.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sample.box.Game;
import com.sample.box.entities.MapInfo;

import java.util.*;

public class BackgroundHelper {

    private OrthographicCamera camera;
    private MapInfo mapInfo = LevelHelper.getGetMapInfo();

    private int numDrawX;
    private int numDrawY;

    private Map<String, TextureRegion> imgMap = new HashMap<String, TextureRegion>();

    private SpriteBatch sb;

    public BackgroundHelper(OrthographicCamera camera,SpriteBatch sb) {
        this.camera = camera;
        this.sb = sb;
    }

    public void init(){
        Texture bgs = mapInfo.getBackground();
        TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 240);
        addImg("sky", sky);
        TextureRegion clouds = new TextureRegion(bgs, 0, 240, 320, 160);
        addImg("clouds", clouds);
        TextureRegion mountains = new TextureRegion(bgs, 2, 410, 316, 100);
        addImg("mounts", mountains);
        TextureRegion back = new TextureRegion(bgs, 2, 515, 316, 175);
        addImg("back", back);

        Texture bf = mapInfo.getBackForest();
        TextureRegion forest = new TextureRegion(bf, 0, 0, 250, 240);
        addImg("forest", forest);
    }

    //draw texture only once in position
    private void drawOnce(TextureRegion image, float x, float y, float scale){

        //somehow get new pos
        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();
        sb.draw(image, px, py);
        sb.end();
    }

    //draw texture only once scale to fullscreen
    private void drawOnceFull(TextureRegion image, float x, float y){
        sb.begin();
        sb.draw(image, x, y, Game.V_WIDTH,Game.V_HEIGHT);
        sb.end();
    }

    //draw texture as line repeatedly with offset
    private void drawRow(TextureRegion image, float x, float y, float offset, float scale){

        //get image cou in x coordinate
        numDrawX = Game.V_WIDTH / image.getRegionWidth() + 2;

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
    private void drawColumn(TextureRegion image, float x, float y, float offset, float scale){

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
    private void drawAll(TextureRegion image, float x, float y, float offsetX, float offsetY, float scale){
        //get image cou in y coordinate
        numDrawY = Game.V_HEIGHT / image.getRegionHeight()+1;
        //get image cou in x coordinate
        numDrawX = Game.V_WIDTH / image.getRegionWidth()+1;

        float px = ((x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float py = ((y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {
                sb.draw(image, px + (col + offsetX) * image.getRegionWidth(), py - (offsetY + row) * image.getRegionHeight());
            }
        }

        sb.end();
    }

    //why so point scale ??????
    public void render(){
        drawOnceFull(getImgByNames("sky"),0,0);
        drawRow(getImgByNames("clouds"),0, 20, 0, 0.1f);
        drawRow(getImgByNames("mounts"),0, 15, 0, 0.2f);
        drawAll(getImgByNames("back"),0, 1500, 0, 0, 0.1f);
        drawRow(getImgByNames("forest"),0, 0.01f, 0, 1);
    }

    private void addImg(String name, TextureRegion img){
        imgMap.put(name, img);
    }

    private Set<String> getImgNames(){
        return imgMap.keySet();
    }

    private TextureRegion getImgByNames(String name){
        return imgMap.get(name);
    }

}
