package com.sample.box.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sample.box.Game;
import com.sample.box.entities.Player;
import com.sample.box.handlers.GameContactListener;
import com.sample.box.handlers.GameInput;
import com.sample.box.handlers.GameStateManager;
import com.sample.box.handlers.B2DVars;

public class Play extends GameState {

    private World world;
    private Body body;
//    private Body playerBody;
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;

    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    private GameContactListener cl;

    private TiledMap tiledMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;

    public Play(GameStateManager gsm){
        super(gsm);

        initWorld();

        //create player
        createPlayer();

        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH/ B2DVars.PPM,Game.V_HEIGHT/ B2DVars.PPM);

        //load tile map
        tiledMap = new TmxMapLoader().load("/home/nikolay/Projects/android/Box/desktop/res/maps/small.tmx");
        tmr = new OrthogonalTiledMapRenderer(tiledMap);

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("floor");
        tileSize = (Integer) tiledMap.getProperties().get("tilewidth");

        createFloorBox(layer);
    }

    public void handleInput(){
        if(GameInput.isPressed(GameInput.JUMP) && cl.isPlayerOnGround()){
            player.getBody().applyForceToCenter(0, 200, true);
        }
        if(GameInput.isDown(GameInput.RIGHT)){
            player.getBody().applyForceToCenter(1, 0, true);
        }
        if(GameInput.isDown(GameInput.LEFT)){
            player.getBody().applyForceToCenter(-1, 0, true);
        }
    }

    public void update(float dt){
        handleInput();
        world.step(dt,6,2);
        player.update(dt);
    }

    public void render(){
        //clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //draw tile map
        tmr.setView(cam);
        tmr.render();
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
        b2dr.render(world,b2dCam.combined);
    }

    public void dispose(){

    }

    private void initWorld(){
        world = new World(new Vector2(0,-9.81f), true);
        cl = new GameContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
    }

    // create player
    private void createPlayer(){

        bdef.position.set(160 /B2DVars.PPM , 200/B2DVars.PPM);
        bdef.type = BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        CircleShape cs = new CircleShape();
        cs.setRadius(7f/B2DVars.PPM);

        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GROUND;
        fdef.shape = cs;
        fdef.restitution = 0.7f;
        body.createFixture(fdef).setUserData("Player");

        // create sensor
        cs.setRadius(8.5f/B2DVars.PPM);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GROUND;
        fdef.shape = cs;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("Foot");
        player = new Player(body);
        body.setUserData(player);
    }

    // go through all the cells in the layer
    private void createFloorBox(TiledMapTileLayer layer){

        for(int row=0; row<layer.getHeight();row++){
            for(int col=0;col<layer.getWidth();col++){
                Cell cell = layer.getCell(col,row);
                // check if cell exists
                if((cell == null)||(cell.getTile() == null)){

                } else {
                    //create body + fixture for cell
                    bdef.type = BodyType.StaticBody;

                    bdef.position.set((col+0.5f)*tileSize/B2DVars.PPM,(row+0.5f)*tileSize/B2DVars.PPM);
                    ChainShape cs = new ChainShape();
                    Vector2[] v = new Vector2[4];
                    v[0] = new Vector2(-tileSize/2/B2DVars.PPM,-tileSize/2/B2DVars.PPM);
                    v[1] = new Vector2(-tileSize/2/B2DVars.PPM,tileSize/2/B2DVars.PPM);
                    v[2] = new Vector2(tileSize/2/B2DVars.PPM,tileSize/2/B2DVars.PPM);
                    v[3] = new Vector2(tileSize/2/B2DVars.PPM,-tileSize/2/B2DVars.PPM);

                    cs.createChain(v);
                    fdef.friction = 0;
                    fdef.shape = cs;
                    fdef.filter.categoryBits = B2DVars.BIT_GROUND;
                    fdef.filter.maskBits = B2DVars.BIT_PLAYER;
                    fdef.isSensor = false;
                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
    }
}