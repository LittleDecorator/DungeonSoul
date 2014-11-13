package com.sample.box.factory;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sample.box.Game;
import com.sample.box.entities.*;
import com.sample.box.handlers.B2DVars;
import com.sample.box.handlers.Content;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;
import com.sample.box.helpers.LevelHelper;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sample.box.helpers.ObjectHelper;

import static com.sample.box.handlers.B2DVars.PPM;
import static com.sample.box.utils.Console.log;

public class LevelFactory {

    private static Array<Point> points = new Array<Point>();
    private static Barrel barrel;

    private static void renderMap(World world,  TiledMap map){
        BodyDef bdef = new BodyDef();                               //create body
        FixtureDef fdef = new FixtureDef();                         //create fixture

        //render floor
        MapLayer floor = map.getLayers().get("floor");         //get specific layer
        //for each object in layer
        //1 cell = 8px in my layer
        PolygonShape pshape = new PolygonShape();           //set body shape
        for(MapObject mo : floor.getObjects()){
            bdef.type = BodyDef.BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/PPM;         //get x coordinate, already multiply by 8
            float y = ((Float)mo.getProperties().get("y"))/PPM;         //get y coordinat, NOT multiply by 8
            float w = Float.parseFloat((String)mo.getProperties().get("width"));        //get width custom property, NOT multiply by 8
            float h = Float.parseFloat((String)mo.getProperties().get("height"));       //get height custom property, NOT multiply by 8
            bdef.position.set(x,y);                                             //set body position in world
            pshape.setAsBox((Game.V_WIDTH/(0.2f*PPM)) - 0.2f, 0/PPM,new Vector2( Game.V_WIDTH/(2*PPM),-0.7f),0);
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_GROUND;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("floor");          //add user data to fixture, as marker
        }

        //render walls
        MapLayer wall = map.getLayers().get("wall");         //get specific layer

        //for each object in layer
        for(MapObject mo : wall.getObjects()){
            bdef.type = BodyDef.BodyType.StaticBody;                        //make it static
            if(mo.getName().contentEquals("left")){
                bdef.position.set(0.2f,0);                                             //set body position in world
                pshape.setAsBox(0, 2.2f, new Vector2(0,2.3f),0);
            } else {
                bdef.position.set(15.2f,0);                                             //set body position in world
                pshape.setAsBox(0, 2.2f, new Vector2(0,2.3f),0);
            }
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_WALL;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("wall");          //add user data to fixture, as marker
        }

        //render platforms
        /*MapLayer platform = map.getLayers().get("platforms");
        for(MapObject mo : platform.getObjects()){
            bdef.type = BodyDef.BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/PPM;         //get x coordinate
            float y = ((Float)mo.getProperties().get("y"))/PPM;         //get y coordinate
            bdef.position.set(x,y);                                             //set body position in world
            pshape.setAsBox(0.2f, 0.01f);
            fdef.shape = pshape;                                                //set shape to fixture
            fdef.friction = 0;
            fdef.filter.categoryBits = B2DVars.BIT_GROUND;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            Body body = world.createBody(bdef);                     //create body
            body.createFixture(fdef).setUserData("platform");          //add user data to fixture, as marker
        }*/

        //create collectables items
        points = new Array<Point>();                                //init array
        MapLayer player = map.getLayers().get("points");         //get specific layer
        GameHelper.getGame().getResource().loadTexture(Sprites.POINTS,"point");     //load point texture
        CircleShape cshape = new CircleShape();                             //set body shape
        //for each object in layer
        for(MapObject mo : player.getObjects()){
            bdef.type = BodyType.StaticBody;                        //make it static
            float x = ((Float)mo.getProperties().get("x"))/PPM;         //get x coordinate
            float y = ((Float)mo.getProperties().get("y"))/PPM;         //get y coordinate
            log("crystal body x = "+x + " ; y = "+ y);
            bdef.position.set(x,y);                                             //set body position in world
            Body body = world.createBody(bdef);                     //create body
            cshape.setRadius(0.04f);                                    //radius
            fdef.shape = cshape;                                                //set shape to fixture
            fdef.isSensor = true;                                   //set fixture as sensor
            fdef.filter.categoryBits = B2DVars.BIT_POINT;           //def filter
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
            body.createFixture(fdef).setUserData("point");          //add user data to fixture, as marker
            Point p = new Point(body);                              //create new object from body
            body.setUserData(p);                                    //link object with body
            points.add(p);                                          // add object to array
        }
        cshape.dispose();

        //create barrel
        bdef.type = BodyDef.BodyType.StaticBody;                        //make it static
        bdef.position.set(5f,.235f);
        Body body = world.createBody(bdef);                     //create body
        pshape.setAsBox(0.13f, 0.13f);
        fdef.shape = pshape;                                                //set shape to fixture
        fdef.friction = 0;
        fdef.filter.categoryBits = B2DVars.BIT_DESTROYABLE | B2DVars.BIT_CONTAINER;           //def filter
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;              //def maskBits
        body.createFixture(fdef).setUserData("barrel");          //add user data to fixture, as marker
        GameHelper.getGame().getResource().loadTexture(Sprites.BARREL,"barrel");     //load point texture
        barrel = new Barrel(body);
        ObjectHelper.addObject("barrel",barrel);
    }

    public static OrthogonalTiledMapRenderer buildEntrance(World world){
        TiledMap tiledMap;

        //load map
        tiledMap = new TmxMapLoader().load(Maps.ENTRANCE);
        renderMap(world, tiledMap);
        MapInfo info = new MapInfo();
        info.setTileMapWidth((Integer) tiledMap.getProperties().get("width"));
        info.setTileMapHeight((Integer) tiledMap.getProperties().get("height"));
        info.setTileSize((Integer) tiledMap.getProperties().get("tilewidth"));
        Content content =  GameHelper.getGame().getResource();
        content.loadTexture(Sprites.SURFACE_BACKGROUND,"background");
        info.setBackground(content.getTexture("background"));
        info.setPoints(points);
        info.setBarrel(barrel);
        LevelHelper.setMapInfo(info);

        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}
