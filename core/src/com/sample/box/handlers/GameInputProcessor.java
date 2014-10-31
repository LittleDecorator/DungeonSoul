package com.sample.box.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sample.box.character.Character;
import com.sample.box.entities.Barrel;
import com.sample.box.entities.DisplayElement;
import com.sample.box.factory.FontFactory;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.ObjectHelper;
import com.sample.box.helpers.ScreenHelper;
import com.sample.box.ui.stage.InventoryScreen;

import static com.sample.box.handlers.B2DVars.PPM;
import static com.sample.box.utils.Console.log;

public class GameInputProcessor extends InputAdapter{

    private static Character body;
    private Vector2 velocity;
    private GameContactListener gcl;
    private boolean flag;
    private static final float JUMP_FORCE = 250;
    private static final float MOVE_SPEED = 40;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && gcl.isPlayerCanHit()){
            ((Barrel)ObjectHelper.getObject("barrel")).hit();
        }
        return true;
    }

    @Override
    public boolean keyDown(int k) {
        if(k== Input.Keys.SPACE && gcl.isPlayerOnGround()){
            velocity.y = JUMP_FORCE;
            flag = true;
        }
        if(k== Input.Keys.D){
//            log("right");
            velocity.x = MOVE_SPEED;
            flag = true;
            GameInput.setKey(GameInput.RIGHT,true);
        }
        if(k== Input.Keys.A){
//            log("left");
            velocity.x = -MOVE_SPEED;
            flag = true;
            GameInput.setKey(GameInput.LEFT,true);
        }
        if(k== Input.Keys.F){
//            log("flash");
            if(body.flameIsOn()){
                body.flameOff();
            } else {
                body.flameOn();
            }
            flag = true;
        }
        if(k == Input.Keys.I){
//            log(GameInput.getKey(GameInput.INVENTORY)?"TRUE":"FALSE");
//            if(!GameInput.getKey(GameInput.INVENTORY)){
                ScreenHelper.getInventory().show();
//                GameInput.setKey(GameInput.INVENTORY,true);
//            }/* else {
//                ScreenHelper.getInventory().hide();
//                GameInput.setKey(GameInput.INVENTORY,false);
//            }*/

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

    public void setBody(Character body) {
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

    public static void drawVelInfo(SpriteBatch sb){
        sb.begin();
        // draw point amount
        FontFactory.getFont8().draw(sb,body.getBody().getLinearVelocity().x +"; "+body.getBody().getLinearVelocity().y, body.getPosition().x * PPM, 1* PPM);
        sb.end();
    }
}
