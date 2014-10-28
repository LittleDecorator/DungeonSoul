package com.sample.box.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.Game;
import com.sample.box.entities.BoundedCamera;
import com.sample.box.handlers.GameStateManager;
import com.sample.box.helpers.GameHelper;

/* some strange class
* bidirectional ref to manager????? WTF???
*
* must provide resource, map, camera, spite access
* */
public abstract class GameState {

    protected Game game;

    protected SpriteBatch sb;
    protected BoundedCamera bCam;
    protected OrthographicCamera oCam;


    protected GameState(){
        game = GameHelper.getGame();
        sb = new SpriteBatch();
        bCam = game.getbCam();
        oCam = game.getoCam();
    }

    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
