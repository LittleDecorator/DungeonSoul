package com.sample.box.state;

import static com.sample.box.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.sample.box.Game;
import com.sample.box.entities.BoundedCamera;
import com.sample.box.entities.Platform;
import com.sample.box.entities.Player;
//import com.sample.box.entities.Point;
import com.sample.box.handlers.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Play extends GameState {

    private World world;
//    private Body body;
//    private Body playerBody;
    private BodyDef bdef;
//    private PolygonShape shape;
    private FixtureDef fdef;

    private Box2DDebugRenderer b2dr;

    private BoundedCamera b2dCam;

    private GameContactListener cl;

    private TiledMap tiledMap;

    private int tileMapWidth;
    private int tileMapHeight;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;
//    private Array<Point> points;
    private Array<Platform> platforms;
    private Background[] backgrounds;

    private boolean debug = true;

    private String playerInfo;
    private String playerPos;
    private String playerForces;
    BitmapFont bitmapFontInfo;

    public Play(GameStateManager gsm){
        super(gsm);

        bitmapFontInfo = new BitmapFont();
        sb = new SpriteBatch();

        initWorld();        //init world

        createPlayer();     //create player

        initMap();

//        createPlatforms();  //create platforms

        createBackground();

        setCam();


    }

    //update world via step
    public void update(float dt){
        world.step(dt,6,2);                 //make step

        cam.update();
        //remove points
        /*Array<Body> bodies = cl.getBodiesToRemove();
        for(int i=0; i < bodies.size;i++){
            points.removeValue((Point)bodies.get(i).getUserData(),true);
            world.destroyBody(bodies.get(i));
            player.collectCrystals();
        }
        bodies.clear();*/

        player.update(dt);                  // update player

        /*for(int i=0; i < points.size;i++){
            points.get(i).update(dt);
        }*/
        displayInfo();
    }

    public void render(){
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);             //clear screen
        tmr.setView(cam);               //draw tile map
        tmr.render();
        sb.setProjectionMatrix(cam.combined);

        player.render(sb);

        //render points
        /*for(int i=0; i < points.size;i++){
            points.get(i).render(sb);
        }*/

        //show box2d objects
        if(debug){
            b2dr.render(world,b2dCam.combined);
        }
        displayInfo();
    }

    public void dispose(){}

    private void initWorld(){
        world = new World(new Vector2(0,-9.81f), true);
        cl = new GameContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();
        bdef = new BodyDef();
//        shape = new PolygonShape();
        fdef = new FixtureDef();
    }

    // create player
    private void createPlayer(){

        bdef.position.set(150 /B2DVars.PPM , 200/B2DVars.PPM);
        bdef.type = BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(8/PPM,12/PPM,new Vector2(-2/PPM,0),0);

        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_POINT | B2DVars.BIT_WALL;
        fdef.shape = ps;
        body.createFixture(fdef).setUserData("Player");

        // create foot sensor
        ps.setAsBox(6/PPM,3/PPM,new Vector2(-2/PPM,-12/PPM),0);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GROUND;
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("footSensor");

        // create left side sensor
        ps.setAsBox(4/PPM,6/PPM,new Vector2(-10/PPM,0),0);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_WALL;
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("leftSensor");

        //create right side sensor
        ps.setAsBox(4/PPM,6/PPM,new Vector2(6/PPM,0),0);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_WALL;
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("rightSensor");

        player = new Player(body);
        body.setUserData(player);

        GameInputProcessor gip = manager.game().getGip();
        gip.setBody(player);
        gip.setGcl(cl);
        Gdx.input.setInputProcessor(gip);
    }

    // go through all the cells in the layer for floor


    //create collectables items
    /*private void createPoints(){
        points = new Array<Point>();                                //init array
        MapLayer layer = tiledMap.getLayers().get("items");         //get specific layer

        BodyDef bdef = new BodyDef();                               //create body
        FixtureDef fdef = new FixtureDef();                         //create fixture

        //for each object in layer
        for(MapObject mo : layer.getObjects()){
            bdef.type = BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/B2DVars.PPM;         //get x coordinate
            float y = ((Float)mo.getProperties().get("y"))/B2DVars.PPM;         //get y coordinate
            bdef.position.set(x,y);                                             //set body position in world
            CircleShape cshape = new CircleShape();                             //set body shape
            cshape.setRadius(8/B2DVars.PPM);                                    //radius
            fdef.shape = cshape;                                                //set shape to fixture
            fdef.isSensor = true;                                   //set fixture as sensor
            fdef.filter.categoryBits = B2DVars.BIT_POINT;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("point");          //add user data to fixture, as marker
            Point p = new Point(body);                              //create new object from body
            points.add(p);                                          // add object to array

            body.setUserData(p);                                    //link object with body
        }
    }*/

    /*private void createPlatforms(){
        platforms = new Array<Platform>();                                //init platform array
        MapLayer layer = tiledMap.getLayers().get("platforms");         //get specific layer

        BodyDef bdef = new BodyDef();                               //create body
        FixtureDef fdef = new FixtureDef();                         //create fixture

        //for each object in layer
        for(MapObject mo : layer.getObjects()){
            bdef.type = BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/PPM;         //get x coordinate
            float y = ((Float)mo.getProperties().get("y"))/PPM;         //get y coordinate
            bdef.position.set(x,y);                                             //set body position in world
            PolygonShape pshape = new PolygonShape();                             //set body shape
            pshape.setAsBox(200 / PPM, 8 / PPM);
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_GROUND;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("platform");          //add user data to fixture, as marker
            Platform p = new Platform(body);                              //create new object from body
            platforms.add(p);                                          // add object to array
            body.setUserData(p);                                    //link object with body
        }
    }*/

    //crate background
    private void createBackground(){
        Texture bgs = Game.res.getTexture("bgs");
        TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 240);
        TextureRegion clouds = new TextureRegion(bgs, 0, 240, 320, 240);
        TextureRegion mountains = new TextureRegion(bgs, 0, 480, 320, 240);
        backgrounds = new Background[3];
        backgrounds[0] = new Background(sky, cam, 0f);
        backgrounds[1] = new Background(clouds, cam, 0.1f);
        backgrounds[2] = new Background(mountains, cam, 0.2f);
    }

    private void setCam(){
        b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
        b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
    }

    /*private void initMap(){
        tiledMap = new TmxMapLoader().load("assets/maps/object_huge_platform.tmx");        //load tile map
        tmr = new OrthogonalTiledMapRenderer(tiledMap);                     //create renderer for map
        tileMapWidth = (Integer)tiledMap.getProperties().get("width");
        tileMapHeight = (Integer)tiledMap.getProperties().get("height");
        tileSize = (Integer)tiledMap.getProperties().get("tilewidth");
    }*/

    // go through all the cells
    private void initMap(){
        tiledMap = new TmxMapLoader().load("assets/maps/small.tmx");
        tmr = new OrthogonalTiledMapRenderer(tiledMap);

        //render floor
        MapLayer floor = tiledMap.getLayers().get("floor");         //get specific layer

        BodyDef bdef = new BodyDef();                               //create body
        FixtureDef fdef = new FixtureDef();                         //create fixture

        //for each object in layer
        //1 cell = 8px in my layer
        for(MapObject mo : floor.getObjects()){
            bdef.type = BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/PPM;         //get x coordinate, already multiply by 8
            float y = ((Float)mo.getProperties().get("y"))/PPM;         //get y coordinat, NOT multiply by 8
            float w = Float.parseFloat((String)mo.getProperties().get("width"));        //get width custom property, NOT multiply by 8
            float h = Float.parseFloat((String)mo.getProperties().get("height"));       //get height custom property, NOT multiply by 8
            /*System.out.println("x = "+ x);
            System.out.println("y = "+ y);
            System.out.println("w = "+ w);
            System.out.println("h = "+ h);
            System.out.println("g_w = "+Game.V_WIDTH);*/
            bdef.position.set(x,y);                                             //set body position in world
            PolygonShape pshape = new PolygonShape();                             //set body shape
            pshape.setAsBox((Game.V_WIDTH/(2*PPM)) - 0.2f, 0/PPM,new Vector2( Game.V_WIDTH/(2*PPM),-0.7f),0);
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_GROUND;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("floor");          //add user data to fixture, as marker
        }
        /*TiledMapTileLayer floor = (TiledMapTileLayer) tiledMap.getLayers().get("floor");
        tileSize = (Integer) tiledMap.getProperties().get("tilewidth");

        for(int row=0; row<floor.getHeight();row++){
            for(int col=0;col<floor.getWidth();col++){
                Cell cell = floor.getCell(col,row);
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
        }*/

        //render walls
        MapLayer wall = tiledMap.getLayers().get("wall");         //get specific layer

        //for each object in layer
        for(MapObject mo : wall.getObjects()){
            bdef.type = BodyType.StaticBody;                        //make it static
            PolygonShape pshape = new PolygonShape();                             //set body shape
            if(mo.getName().contentEquals("left")){
                bdef.position.set(0.2f,0);                                             //set body position in world
                pshape.setAsBox(0, 2.2f, new Vector2(0,2.3f),0);
            } else {
                bdef.position.set(6.2f,0);                                             //set body position in world
                pshape.setAsBox(0, 2.2f, new Vector2(0,2.3f),0);
            }
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_WALL;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("wall");          //add user data to fixture, as marker
        }

        /*TiledMapTileLayer wall = (TiledMapTileLayer) tiledMap.getLayers().get("wall");
        tileSize = (Integer) tiledMap.getProperties().get("tilewidth");

        for(int row=0; row<wall.getHeight();row++){
            for(int col=0;col<wall.getWidth();col++){
                Cell cell = wall.getCell(col,row);
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
        }*/
    }

    private void displayInfo(){
        playerInfo = "fps = " + Gdx.graphics.getFramesPerSecond()+"; is on ground = "+cl.isPlayerOnGround();
        playerPos = "x pos = "+ player.getBody().getPosition().x + "; y pos =  "+ player.getBody().getPosition().y;
        playerForces = "x vel = "+ player.getBody().getLinearVelocity().x + "; y vel = "+player.getBody().getLinearVelocity().y;
        sb.begin();
        bitmapFontInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bitmapFontInfo.draw(sb, playerInfo ,15,400);
        bitmapFontInfo.draw(sb, playerPos ,15,385);
        bitmapFontInfo.draw(sb, playerForces ,15,370);
        sb.end();
    }

}
