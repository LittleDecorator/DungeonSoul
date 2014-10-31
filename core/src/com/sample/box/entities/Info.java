package com.sample.box.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.sample.box.utils.Console.log;

import java.util.ArrayList;
import java.util.List;


/* contain info about objects, fps */
public class Info {

    BitmapFont bitmapFontInfo;

    List<DisplayElement> listInfo = new ArrayList<DisplayElement>();

    public Info() {
        bitmapFontInfo = new BitmapFont();
    }

    /*public void update(Float dt){

    }*/

    public void render(SpriteBatch sb) {
        sb.begin();
        // draw point amount
        for(DisplayElement d : listInfo){
            draw(sb, d);
        }
        sb.end();

    }

    private void draw(SpriteBatch sb, DisplayElement d) {
        bitmapFontInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//        bitmapFontInfo.draw(sb, d.getValue() ,d.getX(), d.getY());
        bitmapFontInfo.draw(sb, "x= "+String.valueOf(d.getX()) + " ; y= "+String.valueOf(d.getY()) ,d.getX(), d.getY());
    }

    public void storeElement(DisplayElement d){
        listInfo.add(d);
    }



}
