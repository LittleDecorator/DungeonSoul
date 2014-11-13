package com.sample.box.state;

import static com.sample.box.handlers.B2DVars.PPM;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sample.box.Game;
import com.sample.box.character.Character;
import com.sample.box.entities.*;
import com.sample.box.factory.LevelFactory;
import com.sample.box.factory.PlayerFactory;
import com.sample.box.handlers.*;
import com.sample.box.helpers.*;
import com.sample.box.ui.stage.InventoryScreen;
import com.sample.box.ui.stage.MenuScreen;

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

    private RayHandler light,flame;         //light

    private Character player;

    private MapInfo mapInfo;

    public Play(){
        initWorld();        //init world
        initMap();          //init map
        initPlayer();       //init player
    }

    //update world via step
    public void update(float dt){
        if(!GameHelper.getGame().isPaused()){
            world.step(dt,6,2);                 //make step
            bCam.update();
            collectPoint();
            player.update(dt);                  // update player
            // update crystals
            for(int i = 0; i < mapInfo.getPoints().size; i++) {
                mapInfo.getPoints().get(i).update(dt);
            }
            mapInfo.getBarrel().update(dt);
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

        //draw flame
        if(player.flameIsOn()) {
            flame = player.getFlame();
            flame.setCombinedMatrix(b2dCam.combined);
            flame.updateAndRender();
            DrawTexture.drawTorch(sb,.5f*PPM,.25f*PPM);
        } else {
            //draw light point
            light.setCombinedMatrix(b2dCam.combined);
            light.updateAndRender();
        }

        //show box2d objects
        if(debug){
            b2dCam.setPosition(player.getPosition().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
            b2dCam.update();
            b2dr.render(world,b2dCam.combined);
        }

        //draw point info
//        InfoHelper.getInfo().render(sb);
        GameInputProcessor.drawVelInfo(sb);

        mapInfo.getBarrel().render(sb);
        ScreenHelper.getInventory().render(Gdx.graphics.getDeltaTime());        //render inventory content
        ScreenHelper.getMenu().render(Gdx.graphics.getDeltaTime());             //render menu modal
    }

    public void dispose(){}

    private void initWorld(){
        world = new World(new Vector2(0,-9.81f), true);
        world.setContactListener(game.getGcl());
        b2dr = new Box2DDebugRenderer();
        StateHelper.setWorld(world);                            //save world ref in state helper
        ScreenHelper.setInventory(new InventoryScreen());       //init inventory screen
        ScreenHelper.setMenu(new MenuScreen());                 //init menu screen
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
        mapInfo = LevelHelper.getGetMapInfo();          //get map info
        createBackground();

        light = new RayHandler(world);                            //add light
        new PointLight(light, 10000, new Color(1,1,1,1), 3f, 5f, 4f).setXray(true);      //create light point
        light.setShadows(false);                        //disaple shadows
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
