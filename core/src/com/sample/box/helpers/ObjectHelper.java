package com.sample.box.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.entities.B2DSprite;
import com.sample.box.entities.Destroyable;

import java.util.HashMap;
import java.util.Map;

public class ObjectHelper {

    private static Map<String,B2DSprite> bodyMap = new HashMap();

    public static void addObject(String name, B2DSprite body){
        bodyMap.put(name, body);
    }

    public static B2DSprite getObject(String name){
        return bodyMap.get(name);
    }
}
