package com.sample.box.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {

    private int numFootContacts;

    // called when two fixtures start to collide
    public void beginContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if(fa.getUserData()!=null && fa.getUserData().equals("Foot")){
            numFootContacts++;
        }
        if(fb.getUserData()!=null && fb.getUserData().equals("Foot")){
            numFootContacts++;
        }
    }

    public boolean isPlayerOnGround(){
        return numFootContacts>0;
    }

    // called when two fixtures end to collide
    public void endContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if(fa.getUserData()!=null && fa.getUserData().equals("Foot")){
            numFootContacts--;
        }
        if(fb.getUserData()!=null && fb.getUserData().equals("Foot")){
            numFootContacts--;
        }
    }

    // called before collision handled but after collision detection
    public void preSolve(Contact c, Manifold m){

    }

    // calling after collision handling
    public void postSolve(Contact c, ContactImpulse ci){

    }


}
