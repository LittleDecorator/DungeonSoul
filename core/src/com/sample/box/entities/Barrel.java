package com.sample.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.sample.box.factory.FontFactory;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.entity.Item;

import static com.sample.box.handlers.B2DVars.PPM;

public class Barrel extends B2DSprite implements Destroyable, Container{

    private TextureRegion fine;
    private TextureRegion broken;
    private TextureRegion currFrame;

    private Array<Item> items;

    private int timerCou =0;
    private float upVal = .001f;

    private int health = 50;

    private int width = 24;
    private int height = 24;

    public Barrel(Body body) {
        super(body);
        InfoHelper.getInfo().storeElement(new DisplayElement(body));
        Texture tex = GameHelper.getGame().getResource().getTexture("barrel");
        TextureRegion[] sprites = TextureRegion.split(tex,24,24)[0];
        fine = sprites[0];
        broken = sprites[1];
        currFrame = fine;

        //fill with items
        items = new Array<Item>();
        items.add(new Item("scroll","simple scroll", TextureHelper.getScroll(), ItemType.OTHER));
        items.add(new Item("bottle","antidote", TextureHelper.getBottle(),ItemType.BOTTLE));
    }

    @Override
    public void update(float dt) {
        if(health==0){
            currFrame = broken;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(currFrame, body.getPosition().x * PPM - width/2, body.getPosition().y * PPM - height/2);
        if(timerCou>0 && timerCou<100){
            FontFactory.getFont8().draw(sb,"10",body.getPosition().x * PPM,(body.getPosition().y+.15f+upVal)* PPM);
            if(timerCou%5==0 || timerCou%5==5){
                upVal = upVal + .005f;
            }
            timerCou++;
        }
        sb.end();
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void hit(){
        if(health>0){
            health -= 10;
            timerCou = 1;
            upVal = .1f;
        }
        System.out.println("health: "+health);
    }

    @Override
    public Array<Item> getInventory() {
        return items;
    }

    @Override
    public void setInventory(Array<Item> items) {
        this.items = items;
    }
}
