package com.sample.box.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sample.box.helpers.GameHelper;
import com.sample.box.ui.actor.MenuActor;

public class MenuScreen implements Screen {

    private MenuActor menuActor;

    public static Stage stage;

    private boolean needRender = false;

    private void init(){
        // create the stage and make it receive all input
        stage = new Stage();

        //get our skin
        Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

        //create actor for screen
        menuActor = new MenuActor(skin);

        //add actor to stage
        stage.addActor(menuActor);
    }

    @Override
    public void render(float delta) {
        if(needRender){
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        if(stage==null){
            init();
        }
        Gdx.input.setInputProcessor(stage);
        menuActor.setVisible(true);
        setNeedRender(true);
    }

    @Override
    public void hide() {
        //hide actor
        menuActor.setVisible(false);
        //set main input handler
        Gdx.input.setInputProcessor(GameHelper.getGame().getGip());
        //skip render
        setNeedRender(false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setNeedRender(boolean needRender) {
        this.needRender = needRender;
    }
}
