package com.sample.box.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.DragSource;
import com.sample.box.ui.DragTarget;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.handler.HideContainerListener;

public class ContainerScreen implements Screen {

//    private Table lootDialog;
    private Window lootDialog;

    DragAndDrop dragAndDrop;

    public static Stage stage;

    private boolean needRender = false;

    private void init(){
        // create the stage and make it receive all input
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

        dragAndDrop = new DragAndDrop();
        lootDialog = createLootDialog(skin);

        stage.addActor(lootDialog);
    }

    @Override
    public void show() {
        if(stage==null){
            init();
        }
        Gdx.input.setInputProcessor(stage);
        lootDialog.setVisible(true);
        setNeedRender(true);
    }

    @Override
    public void render(float delta) {
        if(needRender){
            Table.drawDebug(stage);
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
//        Gdx.input.setInputProcessor(null);
        lootDialog.setVisible(false);
        Gdx.input.setInputProcessor(GameHelper.getGame().getGip());
        setNeedRender(false);
//        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    public void setNeedRender(boolean needRender) {
        this.needRender = needRender;
    }

    //create window
    private Window createLootDialog(Skin skin){

        Window dialog = new Window("Loot dialog", skin);
        dialog.debug();
//        dialog.setBackground(getGray());
        addClose(dialog, skin);           // add header with close button

        dialog.setPosition(300, 250);
        dialog.setSize(620, 320);

        dialog.setMovable(true);

        Table stash = new Table(skin);  //table for character stash items
        Table target = new Table(skin); //table for target items

        stash.debug();
        target.debug();

        //add tables to container
        //set stash table
        Cell cStash = dialog.add(stash);
        cStash.width(300).height(300).space(10);

        //set target table
        Cell cTarget = dialog.add(target);
        cTarget.width(300).height(300).space(10);

        //now try fill inner tables
        createTab(stash, skin, "Player Stash");
        createTab(target, skin, "Target Stash");

        return dialog;
    }

    //pixmap drawable
    private Drawable getGray(){
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.LIGHT_GRAY);
        pm1.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
    }

    //fill table cells with actor containers
    private void createTab(Table table, Skin skin, String header){
        TextField field = new TextField(header,skin);
        field.setDisabled(true);        //set disabled (can't write)
        table.add(field).height(50).colspan(6).fillX();
        table.row();
        for(int i=0 ; i<5;i++){
            for(int j=0;j<6;j++){
                table.add(getDefContainer(null)).width(50).height(50);
            }
            table.row();
        }
    }

    //create cell container
    private Container getDefContainer(TextureRegion tr){
        Container<Image> container = new Container<Image>();
        container.minWidth(50);
        container.minHeight(50);

        ImageActor imageActor;

        if(tr!=null){
            imageActor = new ImageActor(tr);
        } else {
            imageActor = new ImageActor();
        }
        dragAndDrop.addSource(new DragSource(imageActor));
        dragAndDrop.addTarget(new DragTarget(imageActor));
        container.setActor(imageActor);
        container.setBackground(new TextureRegionDrawable(TextureHelper.getCell()));
        return container;
    }

    //add header and close button
    private void addClose(Window w, Skin skin){
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HideContainerListener(w));
        w.getButtonTable().add(closeButton).height(w.getPadTop());
    }
}
