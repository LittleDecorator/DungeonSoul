package com.sample.box.ui.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sample.box.helpers.ScreenHelper;

public class HideInventoryListener extends ClickListener {

    private Actor actor;

    public HideInventoryListener(Actor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        ScreenHelper.getInventory().hide();
    }

}
