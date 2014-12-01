package com.sample.box.state;

import static com.sample.box.handlers.B2DVars.PPM;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.sample.box.helpers.*;
import com.sample.box.ui.stage.ContainerScreen;
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

    private HUD hud;

    private BackgroundHelper bh;

    private boolean debug = true;               //debug flag

    private RayHandler light,flame;         //light

    private Character player;

    private MapInfo mapInfo;

    public Play(){
        initWorld();        //init world
        initMap();          //init map
        initPlayer();       //init player
        initHUD();          //init HUD
    }

    //update world via step
    public void update(){
        if(!GameHelper.getGame().isPaused()){
            world.step(1f/60f,6,2);                 //make step
            bCam.update();
            collectPoint();
            player.update(Gdx.graphics.getDeltaTime());                  // update player
            // update crystals
            for(int i = 0; i < mapInfo.getPoints().size; i++) {
                mapInfo.getPoints().get(i).update(Gdx.graphics.getDeltaTime());
            }
            mapInfo.getBarrel().update(Gdx.graphics.getDeltaTime());
        }
    }

    public void render(){
        // camera follow player
        bCam.setPosition(player.getPosition().x * PPM + Game.V_WIDTH / 4, Game.V_HEIGHT / 2);
        bCam.update();

        // draw background
        sb.setProjectionMatrix(oCam.combined);
        bh.render();
        hud.render(sb);         // WHY ?????

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
            player.renderTorch(b2dCam);
            DrawTexture.drawTorch(sb,player.getFixture("torch"));
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

        //draw collide sprite
        if(GameHelper.getGame().getGcl().isMayLoot()){
            Vector2 playerPos = new Vector2(player.getBody().getPosition());
            SpriteHelper.renderTexture(sb,GameHelper.getGame().getResource().getTexture("hand"),playerPos.x*PPM,(playerPos.y+.3f)*PPM);
        }

        mapInfo.getBarrel().render(sb);
        ScreenHelper.getInventory().render(Gdx.graphics.getDeltaTime());        //render inventory content
        ScreenHelper.getMenu().render(Gdx.graphics.getDeltaTime());             //render menu modal
        ScreenHelper.getContainer().render(Gdx.graphics.getDeltaTime());        //render loot table
    }

    public void dispose(){}

    private void initWorld(){
        world = new World(new Vector2(0,-9.81f), true);
        world.setContactListener(game.getGcl());
        b2dr = new Box2DDebugRenderer();
        StateHelper.setWorld(world);                            //save world ref in state helper
        ScreenHelper.setInventory(new InventoryScreen());       //init inventory screen
        ScreenHelper.setMenu(new MenuScreen());                 //init menu screen
        ScreenHelper.setContainer(new ContainerScreen());            //init loot screen
        //init needed textures
        SpriteHelper.loadAllTextures();
    }

    //crate background
    private void createBackground(){
        bh = new BackgroundHelper(bCam,sb);
        bh.init();
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

    private void initHUD(){
        hud = new HUD(player);
    }

}
