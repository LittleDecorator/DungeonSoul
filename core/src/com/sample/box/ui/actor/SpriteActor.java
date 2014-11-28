package com.sample.box.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.sample.box.ui.Slot;
import com.sample.box.ui.handler.SlotListener;

public class SpriteActor extends ImageButton implements SlotListener{

    private Slot slot;
    private Skin skin;
    private String defName;
    private float scale;

    public SpriteActor(Slot slot, Skin skin, String regName, float scale) {
        super(createStyle(skin, regName, scale, slot));
        this.slot = slot;
        this.skin = skin;
        this.defName = regName;
        this.scale = scale;
    }

    private static ImageButtonStyle createStyle(Skin skin, String regName, float scale, Slot slot) {
        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        TextureAtlas empty = new TextureAtlas(Gdx.files.internal("assets/atlas/bg.atlas"));
        TextureAtlas icons = new TextureAtlas(Gdx.files.internal("assets/atlas/icons.atlas"));
        Sprite image;
        if (slot.getItem() != null) {
            image = new Sprite(icons.findRegion(slot.getItem().getTextureRegion()));
        } else {
            image = new Sprite(empty.findRegion(regName));
            image.scale(scale);
        }
        style.imageUp = new SpriteDrawable(image);
        style.imageDown = new SpriteDrawable(image);
        return style;
    }

    @Override
    public void hasChanged(Slot slot) {
        // when the slot changes, we switch the icon via a new style
        setStyle(createStyle(skin, defName, scale, slot));
    }

    public Slot getSlot() {
        return slot;
    }

}
