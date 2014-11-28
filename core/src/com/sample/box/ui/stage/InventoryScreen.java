package com.sample.box.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.DragSource;
import com.sample.box.ui.DragTarget;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.handler.HideInventoryListener;

public class InventoryScreen implements Screen {

    DragAndDrop dragAndDrop;

//    private Table lootWindow;
    private Window lootWindow;

    public static Stage stage;

    private boolean needRender = false;

    private void init(){
        // create the stage and make it receive all input
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

        dragAndDrop = new DragAndDrop();
        dragAndDrop.setDragActorPosition(-25,25);       //offset of dragable item

        lootWindow = createTestWindow(skin);

        stage.addActor(lootWindow);                 // add window to stage
    }

    @Override
    public void show() {
        if(stage==null){
            init();
        }
        Gdx.input.setInputProcessor(stage);
        lootWindow.setVisible(true);
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
        lootWindow.setVisible(false);
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

    private Window createTestWindow(Skin skin){

        Window inventory = new Window("Inventory",skin);
        addClose(inventory, skin);           // ?????

        inventory.setPosition(300, 250);
        inventory.setSize(500, 420);

        inventory.setMovable(true);

        Table quick = new Table(skin);  //table for quiver,weapons,items
        Table person = new Table(skin); //table for character equip
        Table stats = new Table(skin);  //table for defense abd other
        Table ground = new Table(skin); //table for items on the ground
        Table staff = new Table(skin);  //table for quiver,weapons,items

        quick.debug();
        person.debug();
        stats.debug();
        ground.debug();
        staff.debug();

        //add tables to container
        //set quick table
        Cell cQuick = inventory.add(quick);
        cQuick.width(150).height(300);

        //set person table
        Cell cPerson = inventory.add(person);
        cPerson.width(200).height(300);

        //set stat table
        Cell cStat = inventory.add(stats);
        cStat.width(150).height(300);

        //new row
        inventory.row();

        //set quick table
        Cell cStaff = inventory.add(staff);
        cStaff.width(500).height(100).colspan(3);

        //now try fill inner tables
        createQuick(quick,skin);
        createPerson(person, skin);
        createStaff(staff, skin);

        return inventory;
    }

    private Drawable getGray(){
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.LIGHT_GRAY);
        pm1.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
    }

    private void createQuick(Table table, Skin skin){
        TextField fieldQ = new TextField("Quiver",skin);
        fieldQ.setDisabled(true);
        TextField fieldW = new TextField("Weapon",skin);
        fieldW.setDisabled(true);
        TextField fieldI = new TextField("Items",skin);
        fieldI.setDisabled(true);

        //arrows
        table.add(fieldQ).width(150).height(50).colspan(3).row();
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.row();
        //weapons
        table.add(fieldW).width(150).height(50).colspan(3).row();
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.row();
        //items
        table.add(fieldI).width(150).height(50).colspan(3).row();
        table.add(getDefContainer(TextureHelper.getBottle())).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
    }

    private void createPerson(Table table, Skin skin){
        //head
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.row();

        //person pic
        table.add().width(150).height(200).colspan(3);
        table.add(getDefContainer(null)).width(50).height(50);
        table.row();

        //down
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
        table.add(getDefContainer(null)).width(50).height(50);
    }

    private void createStaff(Table table, Skin skin){
        for(int i=0 ; i<2;i++){
            for(int j=0;j<10;j++){
                table.add(getDefContainer(null)).width(50).height(50);
            }
            table.row();
        }
    }

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

        private void addClose(Window w, Skin skin){
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HideInventoryListener(w));
        w.getButtonTable().add(closeButton).height(w.getPadTop());
    }
}
