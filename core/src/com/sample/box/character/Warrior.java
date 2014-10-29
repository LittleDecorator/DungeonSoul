package com.sample.box.character;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.entities.B2DSprite;
import com.sample.box.entities.DisplayElement;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;
import com.sample.box.helpers.StateHelper;

import static com.sample.box.utils.Console.log;

public class Warrior extends B2DSprite implements Character{

    private int numCrystals;
    private int totalCrystals;
    private RayHandler flame;

    public Warrior(Body body) {
        super(body);
        InfoHelper.getInfo().storeElement(new DisplayElement(body));
        Texture tex = GameHelper.getGame().getResource().getTexture("Player");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
//        animation.setFrames(sprites, 1.5f / 12f);

//        width = sprites[0].getRegionWidth();
//        height = sprites[0].getRegionHeight();
    }

    @Override
    public void render(SpriteBatch sb) {
//        log("Player animation pos: x-> "+body.getPosition().x +" ; y->"+ body.getPosition().y);
        super.render(sb);
    }

    public int getNumCrystals() { return numCrystals; }

    public void collectCrystals(){ numCrystals++; }

    public void setTotalCrystals(int i){ totalCrystals = i; }

    public int getTotalCrystals() { return totalCrystals; }

    public void flameOn(){
        this.flame = new RayHandler(StateHelper.getWorld());
//        Light light = new PointLight(flame, 4, new Color(1,1,1,1), 8f, 5f, 4f);
        Light light = new PointLight(flame, 128, new Color(1,1,1,1), 0.2f, 5f, 4f);
        light.attachToBody(body, 0.05f, 0.05f);
//        light.setColor(MathUtils.random(), MathUtils.random(),MathUtils.random(), 1f);
    }

    @Override
    public void flameOff() {
        flame.dispose();
        flame = null;
    }

    @Override
    public RayHandler getFlame() {
        return flame;
    }

    @Override
    public boolean flameIsOn() {
        return flame!=null;
    }
}
