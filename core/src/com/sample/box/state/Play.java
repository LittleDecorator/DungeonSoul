package com.sample.box.state;

import static com.sample.box.handlers.B2DVars.PPM;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sample.box.Game;
import com.sample.box.character.Character;
import com.sample.box.entities.*;
import com.sample.box.factory.LevelFactory;
import com.sample.box.factory.PlayerFactory;
import com.sample.box.handlers.*;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;
import com.sample.box.helpers.LevelHelper;


import static com.sample.box.utils.Console.log;

/*
* simple game state, accumulate all needed stuff, no implicit constructors, only getters
* */
public class Play extends GameState {

    private World world;        //box2d world

    private Box2DDebugRenderer b2dr;            //world renderer

    private BoundedCamera b2dCam;               //bounded camera instance

    private OrthogonalTiledMapRenderer tmr;     //map renderer

    private Background[] backgrounds;

    private boolean debug = true;               //debug flag

    private RayHandler light;         //light

    private Character player;

    private MapInfo mapInfo;

    public Play(){
        initWorld();        //init world
        initMap();          //init map
        initPlayer();       //init player
    }

    //update world via step
    public void update(float dt){
        world.step(dt,6,2);                 //make step
        bCam.update();
        collectPoint();
        player.update(dt);                  // update player
        // update crystals
        for(int i = 0; i < mapInfo.getPoints().size; i++) {
            mapInfo.getPoints().get(i).update(dt);
        }
    }

    public void render(){
        // camera follow player
        bCam.setPosition(player.getPosition().x * PPM + Game.V_WIDTH / 4, Game.V_HEIGHT / 2);
        bCam.update();

        // draw background
        sb.setProjectionMatrix(oCam.combined);
        for(int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].render(sb);
        }

        // draw tilemap
        tmr.setView(bCam);
        tmr.render();

        //link batch with camera
        sb.setProjectionMatrix(bCam.combined);
        // draw crystals
        for(int i = 0; i < mapInfo.getPoints().size; i++) {
            mapInfo.getPoints().get(i).render(sb);
        }
        // draw player
        player.render(sb);

        //draw light point
        light.setCombinedMatrix(b2dCam.combined);
        light.updateAndRender();

        //show box2d objects
        if(debug){
            b2dCam.setPosition(player.getPosition().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
            b2dCam.update();
            b2dr.render(world,b2dCam.combined);
        }

        //draw point info
        InfoHelper.getInfo().render(sb);
    }

/*    private void updateInfo(){
        info.setPlayerInfo("fps = " + Gdx.graphics.getFramesPerSecond()+"; is on ground = "+cl.isPlayerOnGround());
        info.setPlayerPos("x pos = "+ player.getBody().getPosition().x + "; y pos =  "+ player.getBody().getPosition().y);
        info.setPlayerForces("x vel = "+ player.getBody().getLinearVelocity().x + "; y vel = "+player.getBody().getLinearVelocity().y);
    }*/

    public void dispose(){}

    private void initWorld(){
        world = new World(new Vector2(0,-9.81f), true);
        world.setContactListener(game.getGcl());
        b2dr = new Box2DDebugRenderer();
    }

    //crate background
    private void createBackground(){
        Texture bgs = mapInfo.getBackground();
        TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 240);
        TextureRegion clouds = new TextureRegion(bgs, 0, 240, 320, 240);
        TextureRegion mountains = new TextureRegion(bgs, 0, 480, 320, 240);
        backgrounds = new Background[3];
        backgrounds[0] = new Background(sky, bCam, 0f);
        backgrounds[1] = new Background(clouds, bCam, 0.1f);
        backgrounds[2] = new Background(mountains, bCam, 0.2f);
    }

    private void setCam(){
        bCam.setBounds(0, mapInfo.getTileMapWidth()* mapInfo.getTileSize(), 0, mapInfo.getTileMapHeight() * mapInfo.getTileSize());
        b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
        b2dCam.setBounds(0, (mapInfo.getTileMapWidth()* mapInfo.getTileSize()) / PPM, 0, (mapInfo.getTileMapHeight() * mapInfo.getTileSize()) / PPM);
    }

    private void initMap(){
        tmr = LevelFactory.buildEntrance(world);        //render map
        light = new RayHandler(world);                            //add light
        new PointLight(light, 64, new Color(1,1,1,1), 8f, 5f, 4f);      //create light point
        light.setShadows(false);                        //disaple shadows
        mapInfo = LevelHelper.getGetMapInfo();          //get map info

        createBackground();
        setCam();
    }

    private void initPlayer(){
        player = PlayerFactory.buildWarrior(world);
        //link to input handler
        GameInputProcessor gip = GameHelper.getGame().getGip();
        gip.setBody(player);
        gip.setGcl(GameHelper.getGame().getGcl());
        Gdx.input.setInputProcessor(gip);
    }

    private void collectPoint(){
        //remove points
        Array<Body> bodies = game.getGcl().getBodiesToRemove();
        for(int i=0; i < bodies.size;i++){
            mapInfo.getPoints().removeValue((Point) bodies.get(i).getUserData(), true);
            world.destroyBody(bodies.get(i));
            player.collectCrystals();
        }
        bodies.clear();
    }

}
