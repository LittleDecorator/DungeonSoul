package com.sample.box.character;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface Character{

    public int getNumCrystals();
    public void collectCrystals();
    public void setTotalCrystals(int i);
    public int getTotalCrystals();

    public void flameOn();
    public boolean flameIsOn();
    public void flameOff();
    public RayHandler getFlame();

    public void update(float dt);
    public void render(SpriteBatch sb);
    public Vector2 getPosition();
    public Body getBody();
    public void setBody(Body body);
    public float getWidth();
    public float getHeight();
    public void setAnimation(TextureRegion[] reg, float delay);

}
