/*
package com.sample.box.utils;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;

public class Light {

    */
/** the camera **//*

    RayHandler rayHandler;

    public void create(World world) {
        rayHandler = new RayHandler(world);
//        new PointLight(rayHandler, 32);
        new PointLight(rayHandler, 64, new Color(1,1,1,1), 10f, 5f, 4f);
    }

    public void render(OrthographicCamera cam) {
        rayHandler.setCombinedMatrix(cam.combined);
        rayHandler.updateAndRender();
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }
}
*/
