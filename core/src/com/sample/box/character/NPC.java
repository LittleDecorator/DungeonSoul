package com.sample.box.character;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.ai.AI;

public interface NPC {

    /*defaults*/
//    public TextureRegion image[] = null;
    public AI ai = null;
    public int moveSpeed = 0;
    public boolean isImmortal = true;
    //private Statistic

    /*specific for npc*/
    public String getName();                    //get npc name from his stats

    public boolean canSpeak();                  //dialog possible

    public boolean canTrade();                  //barter possible

    public boolean immortal();                  //flag, that npc is immortal health ignore

    /*all character has*/
    public void update(float dt);               //am I need update ?????

    public void render(SpriteBatch sb);         //render for draw npc

//    public Vector2 getPosition();

    public Body getBody();                      //get npc body

    public void setBody(Body body);             //set body for npc


    /*public float getWidth();

    public float getHeight();*/

    public void setAnimation(TextureRegion[] reg, float delay);             //set npc animation

    public void setTexture(String name, Texture t);          // add texture to texture map

    public void getTexture(String name);        //get texture from map

    public void switchOrient();                 //switch animation direction

    public boolean isRightOrient();             //check direction for animation

    public int getHealth();                     //return npc health

    public State getStartingState();

}
