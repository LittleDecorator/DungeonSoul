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
import com.badlogic.gdx.utils.Array;
import com.sample.box.character.Warrior;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.TextureHelper;
import com.sample.box.ui.drag.DragSource;
import com.sample.box.ui.drag.DragTarget;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.entity.Inventory;
import com.sample.box.ui.entity.Item;
import com.sample.box.ui.entity.Slot;
import com.sample.box.ui.listeners.HideInventoryListener;

public class InventoryScreen implements Screen, StageListener {

    DragAndDrop dragAndDrop;

//    private Table lootWindow;
    private Window lootWindow;

    Table staff;

    Inventory inventory;

    public static Stage stage;

    private boolean needRender = false;

    private void init(){
        // create the stage and make it receive all input
        stage = new Stage();
        inventory = Warrior.getInventory();

        Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

        dragAndDrop = new DragAndDrop();
        dragAndDrop.setDragActorPosition(-25,25);       //offset of dragable item

        lootWindow = createView(skin);

        stage.addActor(lootWindow);                 // add window to stage
    }

    @Override
    public void show() {
        if(stage==null){
            System.out.println("in inventory init");
            init();
        } else {
            fillStashActors();
        }
        System.out.println("in inventory fill");
        Gdx.input.setInputProcessor(stage);
        lootWindow.setVisible(true);
        setNeedRender(true);
    }

    @Override
    public boolean isVisible() {
        if(lootWindow==null){
            return false;
        } else {
            return lootWindow.isVisible();
        }
    }

    @Override
    public void render(float delta) {
        if(needRender){
//            Table.drawDebug(stage);
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
        System.out.println("in inventory stage dispose");
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

    private Window createView(Skin skin){

        Window inventory = new Window("Inventory",skin);
//        Table inventory = new Table(skin);
//        inventory.debug();
        addClose(inventory, skin);           // ?????

        inventory.setPosition(300, 250);
        inventory.setSize(550, 450);

        inventory.setMovable(true);

        Table quick = new Table(skin);  //table for quiver,weapons,items
        Table person = new Table(skin); //table for character equip
        staff = new Table(skin);  //table for quiver,weapons,items

/*        quick.debug();
        person.debug();
        staff.debug();*/

        //add tables to container
        //set quick table
        Cell cQuick = inventory.add(quick);
        cQuick.width(150).height(300).space(5);

        //set person table
        Cell cPerson = inventory.add(person);
        cPerson.width(300).height(300).space(5);

        //new row
        inventory.row();

        //set quick table
        Cell cStaff = inventory.add(staff);
        cStaff.width(500).height(100).colspan(3).space(5);

        //now try fill inner tables
        createQuick(quick,skin);
        createPerson(person);
        createStaff(staff);
        return inventory;
    }

    /*private Drawable getGray(){
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.LIGHT_GRAY);
        pm1.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
    }*/

    private void createQuick(Table table, Skin skin){
        Label qLabel = new Label("Quiver",skin);
        qLabel.setAlignment(Align.center);
        Label wLabel = new Label("Weapon",skin);
        wLabel.setAlignment(Align.center);
        Label iLabel = new Label("Items",skin);
        iLabel.setAlignment(Align.center);

        //arrows
        table.add(qLabel).width(150).height(30).colspan(3).space(0, 0, 5, 0).row();
        for(Slot slot : inventory.getQuiverSlots()){
            dragAndDrop.addSource(new DragSource(slot));
            dragAndDrop.addTarget(new DragTarget(slot));
            table.add(slot).width(50).height(50).space(0,0,15,0);
        }
        table.row();

        //weapons
        table.add(wLabel).width(150).height(30).colspan(3).space(0, 0, 5, 0).row();
        for(Slot slot : inventory.getWeaponSlots()){
            dragAndDrop.addSource(new DragSource(slot));
            dragAndDrop.addTarget(new DragTarget(slot));
            table.add(slot).width(50).height(50).space(0, 0,15, 0);
        }
        table.row();

        //items
        table.add(iLabel).width(150).height(30).colspan(3).space(0, 0, 5, 0).row();
        for(Slot slot : inventory.getItemSlots()){
            dragAndDrop.addSource(new DragSource(slot));
            dragAndDrop.addTarget(new DragTarget(slot));
            table.add(slot).width(50).height(50).space(0, 0,15, 0);
        }
    }

    private void createPerson(Table table){
        VerticalGroup leftArm = new VerticalGroup();
        leftArm.setHeight(100);
        leftArm.addActor(inventory.getLeftShoulder());
        leftArm.addActor(inventory.getLeftHand());

        VerticalGroup rightArm = new VerticalGroup();
        rightArm.setHeight(100);
        rightArm.addActor(inventory.getRightShoulder());
        rightArm.addActor(inventory.getRightHand());

        HorizontalGroup legs = new HorizontalGroup();
        legs.setWidth(100);
        legs.addActor(inventory.getLeftLeg());
        legs.addActor(inventory.getRightLeg());

        HorizontalGroup ankles = new HorizontalGroup();
        ankles.setWidth(100);
        ankles.addActor(inventory.getLeftAnkle());
        ankles.addActor(inventory.getRightAnkle());

        //head
        table.add().width(100).height(50);

        table.add(inventory.getHead());
        table.add(inventory.getAmulet()).spaceLeft(25);
        table.row();

        table.add(leftArm).width(50).height(50).top().right();
        table.add(inventory.getChest()).space(25);
        table.add(rightArm).width(50).height(50).top().left();
        table.row();

        table.add(inventory.getLeftRing()).spaceRight(25);
        table.add(legs).width(50).height(50).left();
        table.add(inventory.getRightRing()).spaceLeft(25);
        table.row();


        table.add().width(100).height(50).left().spaceTop(20);
        table.add(ankles).width(50).height(50).left();
        table.add().width(100).height(50).right();

    }

    private void createStaff(Table table){
        Array<Slot> slots = inventory.getStashSlots();
        for(int i=0 ; i<2;i++){
            for(int j=0;j<10;j++){
                Slot slot = slots.get((i*10)+j);
                dragAndDrop.addSource(new DragSource(slot));
                dragAndDrop.addTarget(new DragTarget(slot));
                table.add(slot).width(50).height(50);
            }
            table.row();
        }
    }

    private void addClose(Window w, Skin skin){
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HideInventoryListener(w));
        w.getButtonTable().add(closeButton).height(w.getPadTop());
    }

    private void fillStashActors(){
        Array<Slot> slots = inventory.getStashSlots();
        Array<Cell> cells = staff.getCells();
        for(int i=0 ; i<2;i++){
            for(int j=0;j<10;j++){
                Slot slot = slots.get((i*10)+j);
                Cell cell = cells.get((i*10)+j);
                dragAndDrop.addSource(new DragSource(slot));
                dragAndDrop.addTarget(new DragTarget(slot));
                cell.setActor(slot);
            }
        }
    }
}
