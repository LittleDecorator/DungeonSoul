package com.sample.box.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.sample.box.character.Warrior;
import com.sample.box.entities.Barrel;
import com.sample.box.entities.Point;
import com.sample.box.helpers.ObjectHelper;
import com.sample.box.ui.stage.ContainerScreen;

import static com.sample.box.utils.Console.log;

public class GameContactListener implements ContactListener {

    private int footContact;
    private int leftWallContact;
    private int rightWallContact;
    private int hermitContact;
    private int enemyContact;
    private Array<Body> bodiesToRemove = new Array<Body>();
    private String dataBuff;

    private int mayLoot;

    // called when two fixtures start to collide
    public void beginContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        dataBuff =(String)fa.getUserData()+fb.getUserData();
        if(dataBuff.contains("footSensor")){
            footContact++;
        }

        //remove point
        if(dataBuff.contains("point")){
            bodiesToRemove.add(fb.getBody());
        }

        //left collide with walls
        if(dataBuff.contains("leftSensor")){
            //if A key was pressed, then skip key release
            GameInput.mark4skip(GameInput.LEFT);
            leftWallContact++;
        }

        //right collide with wall
        if(dataBuff.contains("rightSensor")){
            //if D key was pressed, then skip key release
            GameInput.mark4skip(GameInput.RIGHT);
            rightWallContact++;
        }

        if(dataBuff.contains("hermit")){
            hermitContact++;
        }

        //hit enemy short_range
        if(dataBuff.contains("enemyRightSensor") || dataBuff.contains("enemyLeftSensor")){
            enemyContact++;
        }

        //if player collide with loot objects
        if(dataBuff.contains("barrel") && ((Barrel)ObjectHelper.getObject("barrel")).getHealth()==0){
            if(fb.getBody().getUserData() instanceof Barrel){
                ContainerScreen.setContainerSource((Barrel)fb.getBody().getUserData());
            } else {
                ContainerScreen.setContainerSource((Barrel)fa.getBody().getUserData());
            }
            mayLoot++;
        }
    }

    public boolean isPlayerOnGround(){
        return footContact>0;
    }

    public boolean isLeftSideWall(){
        return leftWallContact>0;
    }

    public boolean isRightSideWall() { return rightWallContact>0; }

    public boolean isPlayerCanHit(){
        return enemyContact>0;
    }

    public boolean isMayLoot(){
        return mayLoot>0;
    }

    public boolean isMaySpeak(){
        return hermitContact>0;
    }

    // called when two fixtures end to collide
    public void endContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        dataBuff = (String)fa.getUserData()+fb.getUserData();

        if(dataBuff.contains("footSensor")){
            footContact--;
        }

        if(dataBuff.contains("leftSensor")){
            leftWallContact--;
        }

        if(dataBuff.contains("rightSensor")){
            rightWallContact--;
        }

        if(dataBuff.contains("enemyRightSensor") || dataBuff.contains("enemyRightSensor")){
            enemyContact--;
        }

        if(dataBuff.contains("barrel") && ((Barrel)ObjectHelper.getObject("barrel")).getHealth()==0){
            mayLoot--;
        }

        if(dataBuff.contains("hermit")){
            hermitContact--;
        }
    }

    // called before collision handled but after collision detection
    public void preSolve(Contact c, Manifold m){

    }

    // calling after collision handling
    public void postSolve(Contact c, ContactImpulse ci){

    }

    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }
}
