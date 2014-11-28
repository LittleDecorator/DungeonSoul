package com.sample.box.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureActor extends ImageButton{

    public TextureActor(Skin skin, String regName) {
        super(createStyle(skin, regName));
    }

    private static ImageButtonStyle createStyle(Skin skin, String regName) {
        TextureAtlas icons = new TextureAtlas(Gdx.files.internal("assets/atlas/bg.atlas"));
        TextureRegion image;
        image = icons.findRegion(regName);
        ImageButtonStyle style = new ImageButton.ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }

}
