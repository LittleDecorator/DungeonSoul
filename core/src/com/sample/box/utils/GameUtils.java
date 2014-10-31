/*
package com.sample.box.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sample.box.entities.BoundedCamera;
import com.sample.box.handlers.Content;
import com.sample.box.handlers.GameContactListener;
import com.sample.box.handlers.GameInputProcessor;
import com.sample.box.handlers.GameStateManager;

public class GameUtils {

    private static BoundedCamera bCam;
    private static OrthographicCamera oCam;

    private static GameStateManager gsm;
    private static GameInputProcessor gip;
    private static GameContactListener gcl;
    private static Content resource;

    public static void init(){
        gip = new GameInputProcessor();                         //input listener
        gcl = new GameContactListener();                        //contact listener
        bCam = new BoundedCamera();                             //bounded camera
        bCam.setToOrtho(false, Parameter.V_WIDTH, Parameter.V_HEIGHT);
        oCam = new OrthographicCamera();                        //camera
        oCam.setToOrtho(false, Parameter.V_WIDTH, Parameter.V_HEIGHT);
//        GameHelper.setGame(this); //create ref via helper
        gsm = new GameStateManager();  //managing game state(views)
    }


    public static BoundedCamera getbCam() {
        return bCam;
    }

    public static OrthographicCamera getoCam() {
        return oCam;
    }

    public static GameStateManager getGsm() {
        return gsm;
    }

    public static GameInputProcessor getGip() {
        return gip;
    }

    public static GameContactListener getGcl() {
        return gcl;
    }

    public static Content getResource() {
        return resource;
    }
}
*/
