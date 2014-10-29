package com.sample.box.entities;

import com.badlogic.gdx.physics.box2d.Body;
import static com.sample.box.utils.Console.log;
import static  com.sample.box.handlers.B2DVars.PPM;

public class DisplayElement {

//    private String value;
    private Body body;

    public DisplayElement(/*String value, */Body body) {
//        this.value = value;
        this.body = body;
    }

    /*public String getVal() {
        if(value==null){
            value = "x= "+this.getX()+" ; y="+this.getY();
        }
        log("value: "+ value);
        return value;
    }*/

    public float getX() {
        return body.getPosition().x * PPM;
    }

    public float getY() {
        return body.getPosition().y * PPM;
    }

}
