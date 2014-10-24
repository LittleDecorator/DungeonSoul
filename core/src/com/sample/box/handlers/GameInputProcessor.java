package com.sample.box.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.sample.box.entities.B2DSprite;
import static com.sample.box.utils.Console.log;

public class GameInputProcessor extends InputAdapter{

    private B2DSprite body;
    private Vector2 velocity;
    private GameContactListener gcl;
    private boolean flag;
    private static final float JUMP_FORCE = 200;
    private static final float MOVE_SPEED = 40;

    //need monitor what key pressed and released

    @Override
    public boolean keyDown(int k) {
        if(k== Input.Keys.SPACE && gcl.isPlayerOnGround()){
            velocity.y = JUMP_FORCE;
            flag = true;
        }
        if(k== Input.Keys.D){
            velocity.x = MOVE_SPEED;
            flag = true;
            GameInput.setKey(GameInput.RIGHT,true);
        }
        if(k== Input.Keys.A){
            velocity.x = -MOVE_SPEED;
            flag = true;
            GameInput.setKey(GameInput.LEFT,true);
        }
        if(flag){
            body.getBody().applyForceToCenter(velocity,true);
            resetVelocity();
            flag = false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int k) {
        if(k == Input.Keys.D || k == Input.Keys.A){
            if(k== Input.Keys.D && !gcl.isRightSideWall() && GameInput.getKey(GameInput.RIGHT)){
                velocity.x = -MOVE_SPEED;
                flag = true;
                GameInput.setKey(GameInput.RIGHT, false);    //mark as readed
            }
            if(k== Input.Keys.A && !gcl.isLeftSideWall() && GameInput.getKey(GameInput.LEFT)){
                velocity.x = MOVE_SPEED;
                flag = true;
                GameInput.setKey(GameInput.LEFT,false);
            }
            if(flag){
                body.getBody().applyForceToCenter(velocity,true);
                resetVelocity();
                flag = false;
            }
        }
        return true;
    }

    public void setBody(B2DSprite body) {
        this.body = body;
        this.velocity = body.getBody().getLinearVelocity().cpy();
    }

    public void setGcl(GameContactListener gcl){
        this.gcl = gcl;
    }

    private void resetVelocity(){
        velocity.x = 0;
        velocity.y = 0;
    }
}
