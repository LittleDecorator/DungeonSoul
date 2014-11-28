package com.sample.box.ui.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

public class DefaultContainer extends Container{

    float x,y;


    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawBackground(batch, parentAlpha, x , y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
