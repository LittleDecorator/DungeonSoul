package com.sample.box.helpers;

import com.sample.box.Game;

public class GameHelper {

    private static Game game;

    public static void setGame(Game g) {
        game = g;
    }

    public static Game getGame(){
        return game;
    }
}
