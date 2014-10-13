package com.sample.box.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.sample.box.entities.B2DSprite;

public class GameInputProcessor extends InputAdapter{

    private B2DSprite body;
    private Vector2 velocity;

    @Override
    public boolean keyDown(int k) {
        System.out.println("key Down");
        if(k== Input.Keys.SPACE){
            velocity.y = 300;
        }
        if(k== Input.Keys.D){
            velocity.x =20;
        }
        if(k== Input.Keys.A){
            velocity.x=-20;
        }
        body.getBody().applyForceToCenter(velocity,true);
        return true;
    }

    @Override
    public boolean keyUp(int k) {
        System.out.println("key UP");
        if(k== Input.Keys.SPACE){
            velocity.y =0;
        }
        if(k== Input.Keys.D){
            velocity.x=0;
        }
        if(k== Input.Keys.A){
            velocity.x=0;
        }
        body.getBody().applyForceToCenter(velocity,false);
        return true;
    }

    public void setBody(B2DSprite body) {
        this.body = body;
        this.velocity = body.getBody().getLinearVelocity().cpy();
    }
}
