package com.sample.box.ui.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.sample.box.ui.handler.HideMenuListener;

public class MenuActor extends Window {

    public MenuActor(Skin skin) {
        super("Menu", skin);

        // add an "X" button to the top right of the window, and make it hide the inventory
        TextButton closeButton = new TextButton("X", skin);
        //add button listener
        closeButton.addListener(new HideMenuListener(this));
        //work with window as table
        getButtonTable().add(closeButton).height(getPadTop());

        //some buttons
        TextButton buttonNew = new TextButton("New Game", skin);
        TextButton buttonSave = new TextButton("Save Game", skin);
        TextButton buttonLoad = new TextButton("Load Game", skin);
        TextButton buttonExit = new TextButton("Exit", skin);

        // basic layout prop
        setPosition(400, 100);
        defaults().space(8);
        row().fill().expandX();             //why we need this????

        //The elements are displayed in the order you add them.
        //The first appear on top, the last at the bottom.
        add(buttonNew).size(150,30).padBottom(20).row();
        add(buttonSave).size(150,30).padBottom(20).row();
        add(buttonLoad).size(150,30).padBottom(20).row();
        add(buttonExit).size(150,30).padBottom(20).row();

        pack();                             //why we need this????

        //hide our actor, until explicitly show
        setVisible(false);
    }
}
