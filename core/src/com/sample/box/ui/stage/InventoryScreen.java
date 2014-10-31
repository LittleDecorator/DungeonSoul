package com.sample.box.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.sample.box.helpers.GameHelper;
import com.sample.box.ui.Inventory;
import com.sample.box.ui.actor.InventoryActor;

public class InventoryScreen implements Screen {

    private InventoryActor inventoryActor;

    public static Stage stage;

    private boolean needRender = false;

    private void init(){
        // create the stage and make it receive all input
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

        DragAndDrop dragAndDrop = new DragAndDrop();
        inventoryActor = new InventoryActor(new Inventory(), dragAndDrop, skin);
        stage.addActor(inventoryActor);
    }

    @Override
    public void show() {
        if(stage==null){
            init();
        }
        Gdx.input.setInputProcessor(stage);
        inventoryActor.setVisible(true);
        setNeedRender(true);
    }

    @Override
    public void render(float delta) {
        if(needRender){
            // show the inventory when any key is pressed
//            inventoryActor.setVisible(true);
            // handle all inputs and draw the whole UI
            stage.act(delta);
            stage.draw();
        }/* else if(stage !=null) {
            inventoryActor.setVisible(false);
        }*/
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
//        Gdx.input.setInputProcessor(null);
        inventoryActor.setVisible(false);
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

    public InventoryActor getInventoryActor() {
        return inventoryActor;
    }
}
