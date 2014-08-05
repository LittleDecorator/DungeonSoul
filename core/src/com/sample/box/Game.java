package com.sample.box;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.handlers.Content;
import com.sample.box.handlers.GameInput;
import com.sample.box.handlers.GameInputProcessor;
import com.sample.box.handlers.GameStateManager;

public class Game implements ApplicationListener {

    public static final String TITLE = "AntVenture";
    public static final int V_HEIGHT = 240;
    public static final int V_WIDTH = 320;
    public static final int SCALE =2;

    public static final float STEP = 1/60f;
    private float accum;

    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private GameStateManager gsm;

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
        Gdx.input.setInputProcessor(new GameInputProcessor());
        res = new Content();
        res.loadTexture("img/volleyball_ball.png","ball");
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,Game.V_WIDTH,Game.V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false,Game.V_WIDTH,Game.V_HEIGHT);
        gsm = new GameStateManager(this);
    }

    public void render(){
        accum += Gdx.graphics.getDeltaTime();
        while(accum>=STEP){
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
            GameInput.update();
        }
    }

    public void dispose(){}

    public void resize(int w, int h){}
    public void pause(){}
    public void resume(){}

}

