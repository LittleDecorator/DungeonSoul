package com.sample.box.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter{

    @Override
    public boolean keyDown(int k) {
        if(k== Input.Keys.SPACE){
            GameInput.setKey(GameInput.JUMP,true);
        }
        if(k== Input.Keys.D){
            GameInput.setKey(GameInput.RIGHT,true);
        }
        if(k== Input.Keys.A){
            GameInput.setKey(GameInput.LEFT,true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int k) {
        if(k== Input.Keys.SPACE){
            GameInput.setKey(GameInput.JUMP,false);
        }
        if(k== Input.Keys.D){
            GameInput.setKey(GameInput.RIGHT,false);
        }
        if(k== Input.Keys.A){
            GameInput.setKey(GameInput.LEFT,false);
        }
        return true;
    }
}
