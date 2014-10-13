package com.sample.box;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.entities.BoundedCamera;
import com.sample.box.handlers.Content;
import com.sample.box.handlers.GameInput;
import com.sample.box.handlers.GameInputProcessor;
import com.sample.box.handlers.GameStateManager;

public class Game implements ApplicationListener {

    public static final String TITLE = "AntVenture";
    public static final int V_HEIGHT = 480;
    public static final int V_WIDTH = 640;
    public static final int SCALE =2;

    public static final float STEP = 1/60f;
    private float accum;

    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private GameStateManager gsm;
    private GameInputProcessor gip;

    public static Content res;

    public SpriteBatch getBatch() {
        return sb;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

    public void create(){
        gip = new GameInputProcessor();
        res = new Content();
//        res.loadTexture("assets/img/green_light.png","point");
        res.loadTexture("assets/img/test2.png","player");
        res.loadTexture("assets/img/bgs.png", "bgs");
        sb = new SpriteBatch();
        cam = new BoundedCamera();
        cam.setToOrtho(false,Game.V_WIDTH,Game.V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false,Game.V_WIDTH,Game.V_HEIGHT);
        gsm = new GameStateManager(this);
    }

    public void render(){

//        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        accum += Gdx.graphics.getDeltaTime();
        while(accum>=STEP){
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
//            GameInput.update();
        }
    }

    public void dispose(){}

    public void resize(int w, int h){}
    public void pause(){}
    public void resume(){}

    public GameInputProcessor getGip() {
        return gip;
    }
}

