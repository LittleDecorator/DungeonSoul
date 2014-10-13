package com.sample.box.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.Game;
import com.sample.box.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager manager;
    protected Game game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;


    protected GameState(GameStateManager gsm){
        manager = gsm;
        game = manager.game();
        sb = game.getBatch();
        cam = game.getCamera();
        hudCam = game.getHudCam();
    }

//    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
