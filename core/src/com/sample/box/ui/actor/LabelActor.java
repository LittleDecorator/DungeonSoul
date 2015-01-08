package com.sample.box.ui.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LabelActor extends Label{

    String hiddenInput;
    Integer id;

    public LabelActor(CharSequence text, Skin skin) {
        super(text, skin);
    }

    public LabelActor(CharSequence text, Skin skin, String fontName, String colorName) {
        super(text, skin, fontName, colorName);
    }

    public LabelActor(CharSequence text, LabelStyle style) {
        super(text, style);
    }

    public String getHiddenInput() {
        return hiddenInput;
    }

    public void setHiddenInput(String hiddenInput) {
        this.hiddenInput = hiddenInput;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
