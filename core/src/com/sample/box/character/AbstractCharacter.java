package com.sample.box.character;

import box2dLight.RayHandler;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.entities.PositionedFixture;
import com.sample.box.handlers.Animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.sample.box.handlers.B2DVars.PPM;

public class AbstractCharacter implements Character {

    protected Body body;                //character body
    protected Animation animation;      //character animation
    protected Map<String,PositionedFixture> fixtureMap = new HashMap<String, PositionedFixture>();          //character fixture map

    protected float width;              //character body width
    protected float height;             //character body height

    private Stack<State> history = new Stack<State>();          //character state history

    public AbstractCharacter(Body body, State startingState) {
        this.body = body;
        animation = new Animation();
        addState(startingState);
    }

    public AbstractCharacter(Body body) {
        this.body = body;
        animation = new Animation();
        addState(this.getStartingState());
    }

    /* set animation */
    public void setAnimation(TextureRegion reg, float delay) {
        setAnimation(new TextureRegion[] { reg }, delay);
    }

    /* set animation */
    public void setAnimation(TextureRegion[] reg, float delay){
        animation.setFrames(reg,delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    /* update animation */
    public void update(float dt){
        animation.update(dt);
    }

    /* render animation */
    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(animation.getFrame(), body.getPosition().x * PPM - width/2, body.getPosition().y * PPM - height/2);
        sb.end();
    }

    /* return body */
    public Body getBody(){ return body; }

    /* set body */
    public void setBody(Body body) {
        this.body = body;
    }

    /* get character position */
    public Vector2 getPosition(){ return body.getPosition(); }

    /* get width */
    public float getWidth(){ return width; }

    /* get height */
    public float getHeight(){ return height; }

    /* add state in history stack */
    public void addState(State state) {
        history.push(state);
    }

    public void changeState(State state) {
        history.pop();
        history.push(state);
    }

    public void removeState() {
        //only for when maintaining history
        if (history.size() > 1)
            history.pop();
        else throw new UnsupportedOperationException
                ("removing state from "+this.getClass()+"'s state "+history.peek());
    }

    public State getState(int index) {
        //you can check for illegal argument but I think stack trace is enough
        return history.get(index);
    }

    public State getState() {
        return history.peek();
    }


    /* old one keep while */

    @Override
    public int getNumCrystals() {
        return 0;
    }

    @Override
    public void collectCrystals() {

    }

    @Override
    public void setTotalCrystals(int i) {

    }

    @Override
    public int getTotalCrystals() {
        return 0;
    }

    @Override
    public void flameOn() {

    }

    @Override
    public boolean flameIsOn() {
        return false;
    }

    @Override
    public void flameOff() {

    }

    @Override
    public RayHandler getFlame() {
        return null;
    }

    @Override
    public PositionedFixture getFixture(String key) {
        return null;
    }

    @Override
    public void renderTorch(OrthographicCamera c) {

    }

    @Override
    public void switchOrient() {

    }

    @Override
    public boolean isRightOrient() {
        return false;
    }

    @Override
    public int getLifeContainersCou() {
        return 0;
    }

    @Override
    public State getStartingState() {
        return null;
    }
}
