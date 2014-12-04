package com.sample.box.character;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.sample.box.entities.B2DSprite;
import com.sample.box.entities.DisplayElement;
import com.sample.box.entities.PositionedFixture;
import com.sample.box.helpers.DrawTexture;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.InfoHelper;
import com.sample.box.helpers.StateHelper;
import com.sample.box.ui.entity.Inventory;
import com.sample.box.ui.entity.Item;

import java.util.*;

import static com.sample.box.utils.Console.log;

public class Warrior extends B2DSprite implements Character{

    private int numCrystals;
    private int totalCrystals;
    private RayHandler flame;
    private Light torch;
    private int lifeConteinersCou = 4;

    public boolean rightOrient = true;

    private static Inventory inventory;

    public Warrior(Body body) {
        super(body);
        inventory = new Inventory();
        InfoHelper.getInfo().storeElement(new DisplayElement(body));
        Texture tex = GameHelper.getGame().getResource().getTexture("player");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
    }

    //set new animation for player
    public void setRightAnimation(){
        Texture tex = GameHelper.getGame().getResource().getTexture("player");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
    }

    //set new animation for player
    public void setLeftAnimation(){
        Texture tex = GameHelper.getGame().getResource().getTexture("player_l");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        List<TextureRegion> list = Arrays.asList(sprites);
        Collections.reverse(list);
        sprites = list.toArray(new TextureRegion[]{});
        setAnimation(sprites, 1.5f / 12f);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        renderMirror();
    }

    //mirroring torch fixture(texture course it link)
    private void renderMirror(){
        PositionedFixture fixture = getFixture("torch");
        if(rightOrient){
            fixture.setPoint(new Vector2(body.getPosition().x, body.getPosition().y));
        } else {
            fixture.setPoint(new Vector2(body.getPosition().x - 0.25f, body.getPosition().y));
        }
        fixtureMap.put("torch",fixture);
    }

    public void renderTorch(OrthographicCamera c){
        torch.setPosition(getFixture("torch").getPoint().x, getFixture("torch").getPoint().y);
        flame.setCombinedMatrix(c.combined);
        flame.updateAndRender();
    }

    public int getNumCrystals() { return numCrystals; }

    public void collectCrystals(){ numCrystals++; }

    public void setTotalCrystals(int i){ totalCrystals = i; }

    public int getTotalCrystals() { return totalCrystals; }

    public void flameOn(){
        this.flame = new RayHandler(StateHelper.getWorld());

        torch = new PointLight(flame, 1000, new Color(1,.6f,0,.6f), 2f, getFixture("torch").getPoint().x, getFixture("torch").getPoint().y);
        torch.setXray(true);
        torch.setSoft(true);
        torch.setSoftnessLength(1.5f);
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

    public PositionedFixture getFixture(String key){
        return fixtureMap.get(key);
    }

    public void addFixture(String key,PositionedFixture fixture){
        fixtureMap.put(key,fixture);
    }

    @Override
    public void switchOrient() {
        System.out.println(rightOrient? "TRUE" : "FALSE");
        rightOrient = !rightOrient;
    }

    public boolean isRightOrient(){
        return rightOrient;
    }

    public int getLifeContainersCou() {
        return lifeConteinersCou;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static void setInventory(Inventory inventory) {
        Warrior.inventory = inventory;
    }



    /////////////////////////////////


    @Override
    public State getStartingState() {
        return null;
    }
}
