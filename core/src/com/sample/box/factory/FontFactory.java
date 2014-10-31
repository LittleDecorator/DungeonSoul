package com.sample.box.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontFactory {

    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/test.ttf"));
    private static BitmapFont font8;

    public static BitmapFont getFont8(){
        if(font8==null){
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = 8;
            generateFont8(parameter);
        }
        return font8;
    }

    private static void generateFont8(FreeTypeFontParameter param){
        font8 = generator.generateFont(param);
    }

}
