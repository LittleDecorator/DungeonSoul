package com.sample.box.handlers;

import com.sample.box.Game;
import com.sample.box.state.GameState;
import com.sample.box.state.Play;

import java.util.Stack;

public class GameStateManager {

    private Game game;
    private Stack<GameState> gameStates;
    public final static int PLAY = 912837;

    public GameStateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    public void update(float dt){
        gameStates.peek().update(dt);
    }

    public void render(){
        gameStates.peek().render();
    }

    public Game game() {
        return game;
    }

    private GameState getState(int state){
        if(state == PLAY) return new Play(this);
        return null;
    }

    public void setState(int state){
        popState();
        pushState(state);
    }

    public void popState(){
        GameState state = gameStates.pop();
        state.dispose();
    }

    public void pushState(int state){
        gameStates.push(getState(state));
    }
}
