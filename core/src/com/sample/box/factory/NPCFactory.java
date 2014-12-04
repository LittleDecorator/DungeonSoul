package com.sample.box.factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sample.box.character.AbstractCharacter;
import com.sample.box.character.Hermit;
import com.sample.box.entities.Sprites;
import com.sample.box.handlers.B2DVars;
import com.sample.box.helpers.GameHelper;

import static com.sample.box.handlers.B2DVars.PPM;

public class NPCFactory {

    public static AbstractCharacter buildHermit(World world){
        //create player body
        Body body = build(world);
        //link resource
        GameHelper.getGame().getResource().loadTexture(Sprites.WARRIOR,"hermit");
        Hermit hermit = new Hermit(body);
        body.setUserData(hermit);
        return hermit;
    }

    private static Body build(World world){
        BodyDef bdef = new BodyDef();       //body definition
        FixtureDef fdef = new FixtureDef();    //body fixture definition

        //body type def
        bdef.position.set(2.5f, 0.5f);
        bdef.type = BodyDef.BodyType.DynamicBody;

        //create container and shape
        Body body = world.createBody(bdef);
        PolygonShape ps = new PolygonShape();

        //player body
        ps.setAsBox(8/PPM,12/PPM,new Vector2(-2/PPM,0),0);
        fdef.filter.categoryBits = B2DVars.BIT_NPC;
        fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_PLAYER;
        fdef.shape = ps;
        body.createFixture(fdef).setUserData("hermit");

        return body;
    }

}
