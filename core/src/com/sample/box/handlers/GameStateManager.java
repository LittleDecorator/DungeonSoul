package com.sample.box.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sample.box.Game;
import com.sample.box.state.GameState;
import com.sample.box.state.Play;

import java.util.Stack;
/*
* must be simple state manager, nothing provide
* */
public class GameStateManager {

    private Stack<GameState> gameStates;            //stack of game states
    public final static int PLAY = 912837;          //don't know why we need this

    public GameStateManager() {
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    //get current game state update by delta and render
    public void updateAndRender(float dt){
        GameState curr = gameStates.peek();
        curr.update(dt);
        curr.render();
    }

    //return play state
    public GameState getState(int state){
        if(state == PLAY) return new Play();
        return null;
    }

    //set new state to stack
    public void setState(int state){
        popState();
        pushState(state);
    }

    //i think remove state
    private void popState(){
        GameState state = gameStates.pop();
        state.dispose();
    }

    //add state
    private void pushState(int state){
        gameStates.push(getState(state));
    }

}
