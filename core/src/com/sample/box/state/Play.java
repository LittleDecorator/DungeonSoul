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

    private Map<String,String> playerInfo = new HashMap<String, String>();
    BitmapFont bitmapFontInfo;

    public Play(GameStateManager gsm){
        super(gsm);

        bitmapFontInfo = new BitmapFont();
        sb = new SpriteBatch();

        initWorld();        //init world

        createPlayer();     //create player

        initMap();

        createPlatforms();  //create platforms

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
        playerInfo.clear();
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
        playerInfo.clear();
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

        CircleShape cs = new CircleShape();
        cs.setRadius(7f/B2DVars.PPM);

        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_POINT;
        fdef.shape = cs;
//        fdef.restitution = 0.7f;
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

        GameInputProcessor gip = manager.game().getGip();
        gip.setBody(player);
        Gdx.input.setInputProcessor(gip);
    }

    // go through all the cells in the layer for floor
    /*private void createFloorBox(TiledMapTileLayer layer){

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
    }*/

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

    private void createPlatforms(){
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
    }

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

    private void initMap(){
        tiledMap = new TmxMapLoader().load("assets/maps/object_huge_platform.tmx");        //load tile map
        tmr = new OrthogonalTiledMapRenderer(tiledMap);                     //create renderer for map
        tileMapWidth = (Integer)tiledMap.getProperties().get("width");
        tileMapHeight = (Integer)tiledMap.getProperties().get("height");
        tileSize = (Integer)tiledMap.getProperties().get("tilewidth");
    }

    private void displayInfo(){
        playerInfo.put("fps",String.valueOf(Gdx.graphics.getFramesPerSecond()));
        int i = 0;
        sb.begin();
        bitmapFontInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        for(Map.Entry<String,String> e : playerInfo.entrySet()){
            bitmapFontInfo.draw(sb,e.getKey()+" : "+e.getValue(),15,75+(15*i));
            i++;
        }
        sb.end();
    }

}
