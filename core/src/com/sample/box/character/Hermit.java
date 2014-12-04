package com.sample.box.character;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sample.box.ai.HermitState;
import com.sample.box.helpers.GameHelper;

import java.util.Map;
import java.util.Random;

/* old man, we find him in beginning */
public class Hermit extends AbstractCharacter{

    private Map<String,Texture> textures;

//    private boolean stateLocked = true;
    private float lockTime = 5f;

    private Vector2 velocity = new Vector2();
    private int SPEED = 2;
    private int CURR_SPEED = 0;

    private TextureRegion animation[] = null;

    public StateMachine<Hermit> stateMachine;

    public Hermit(Body body) {
        super(body);
        stateMachine = new DefaultStateMachine<Hermit>(this, HermitState.SLEEP);
        Texture tex = GameHelper.getGame().getResource().getTexture("hermit");
        TextureRegion[] sprites = TextureRegion.split(tex,32,30)[0];
        setAnimation(sprites, 1.5f / 12f);
    }

    public Hermit(Body body, Texture texture) {
        super(body);
        animation = TextureRegion.split(texture,32,30)[0];
        setAnimation(animation, 1.5f / 12f);
        stateMachine = new DefaultStateMachine<Hermit>(this, HermitState.SLEEP);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        lockTime = lockTime - dt;
        if(lockTime<=0){
//            stateLocked = false;
            switchState();
            lockTime = 5f;
//            getStatePeriod();
            stateMachine.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    /*private float getStatePeriod(){
        stateLocked = true;
        return 5f;//new Random().nextFloat() * 50f + 50f;
    }*/

/*    public boolean isStateLocked() {
        return stateLocked;
    }

    public Vector2 getVelocity() {
        return velocity;
    }*/

    public void switchState(){
        int var = new Random().nextInt(3);
        System.out.println("RAND INT -> " + var);
        switch(var){
            case 2:
                //go right
                if(CURR_SPEED<=0){
                    if(CURR_SPEED == 0){
                        CURR_SPEED = SPEED;
                    } else if(CURR_SPEED <0 ){
//                    CURR_SPEED = -CURR_SPEED + SPEED;
                        stopBody();
                        CURR_SPEED = SPEED;
                    }
                    System.out.println("GO RIGHT WITH SPEED -> "+ CURR_SPEED);
                    velocity.x = CURR_SPEED;
                    body.applyForceToCenter(velocity, true);
                }
                stateMachine.changeState(HermitState.WALKING);
                break;
            case 1:
                //go left
                if(CURR_SPEED>=0){
                    if(CURR_SPEED == 0){
                        CURR_SPEED = -SPEED;
                    } else if(CURR_SPEED >0 ){
//                    CURR_SPEED = -(CURR_SPEED + SPEED);
                        stopBody();
                        CURR_SPEED = -SPEED;
                    }
                    System.out.println("GO LEFT WITH SPEED -> "+ CURR_SPEED);
                    velocity.x = CURR_SPEED;
                    body.applyForceToCenter(velocity, true);
                }
                stateMachine.changeState(HermitState.WALKING);
                break;
            case 0:
                if(CURR_SPEED!=0){
                    velocity.x = -CURR_SPEED;
                    CURR_SPEED = 0;
                }
                System.out.println("STAY WITH SPEED -> "+CURR_SPEED);
                body.applyForceToCenter(velocity, true);
                stateMachine.changeState(HermitState.SLEEP);
                break;
        }
    }

    private void stopBody(){
        velocity.x = -CURR_SPEED;
        CURR_SPEED = 0;
        body.applyForceToCenter(velocity, true);
    }
}
