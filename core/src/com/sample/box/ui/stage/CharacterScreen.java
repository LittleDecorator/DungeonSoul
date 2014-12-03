package com.sample.box.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sample.box.helpers.GameHelper;
import com.sample.box.helpers.ScreenHelper;

public class CharacterScreen implements Screen {

    public static Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("assets/skins/uiskin.json"));

    private Window character = new Window("Character",skin);

    private boolean needRender = false;

    private void init(){
        stage = new Stage();
        fillWithStat();
        stage.addActor(character);
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
    public void show() {
        if(stage==null){
            init();
        }
        Gdx.input.setInputProcessor(stage);
        character.setVisible(true);
        setNeedRender(true);
    }

    @Override
    public void hide() {
        character.setVisible(false);
        Gdx.input.setInputProcessor(GameHelper.getGame().getGip());
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

    private void fillWithStat(){

//        character.debug();
        character.setPosition(300, 250);
        character.setSize(400, 300);
        character.setMovable(true);

        VerticalGroup stats = new VerticalGroup();

        //Strength
        HorizontalGroup strength = new HorizontalGroup();
        Label sName = new Label("Strength:",skin);
        Label sValue = new Label("8",skin);
        TextButton sUpStat = new TextButton("+",skin);
        TextButton sDownStat = new TextButton("-",skin);

        strength.addActor(new Container<Label>(sName).width(90).padLeft(5));
        strength.addActor(new Container<Label>(sValue).width(20).left().pad(5));
        strength.addActor(new Container<TextButton>(sUpStat).height(30).width(30));
        strength.addActor(new Container<TextButton>(sDownStat).height(30).width(30));
        stats.addActor(strength);

        //Agility
        HorizontalGroup agility = new HorizontalGroup();
        Label aName = new Label("Agility:",skin);
        Label aValue = new Label("8",skin);
        TextButton aUpStat = new TextButton("+",skin);
        TextButton aDownStat = new TextButton("-",skin);

        agility.addActor(new Container<Label>(aName).width(90).padLeft(5));
        agility.addActor(new Container<Label>(aValue).width(20).left().pad(5));
        agility.addActor(new Container<TextButton>(aUpStat).height(30).width(30));
        agility.addActor(new Container<TextButton>(aDownStat).height(30).width(30));
        stats.addActor(agility);

        //Perception
        HorizontalGroup perception = new HorizontalGroup();
        Label pName = new Label("Perception:",skin);
        Label pValue = new Label("8",skin);
        TextButton pUpStat = new TextButton("+",skin);
        TextButton pDownStat = new TextButton("-",skin);

        perception.addActor(new Container<Label>(pName).width(90).padLeft(5));
        perception.addActor(new Container<Label>(pValue).width(20).left().pad(5));
        perception.addActor(new Container<TextButton>(pUpStat).height(30).width(30));
        perception.addActor(new Container<TextButton>(pDownStat).height(30).width(30));
        stats.addActor(perception);

        //Intelligence
        HorizontalGroup intelligence = new HorizontalGroup();
        Label iName = new Label("Intelligence:",skin);
        Label iValue = new Label("8",skin);
        TextButton iUpStat = new TextButton("+",skin);
        TextButton iDownStat = new TextButton("-",skin);

        intelligence.addActor(new Container<Label>(iName).width(90).padLeft(5));
        intelligence.addActor(new Container<Label>(iValue).width(20).left().pad(5));
        intelligence.addActor(new Container<TextButton>(iUpStat).height(30).width(30));
        intelligence.addActor(new Container<TextButton>(iDownStat).height(30).width(30));
        stats.addActor(intelligence);

        //Wisdom
        HorizontalGroup wisdom = new HorizontalGroup();
        Label wName = new Label("Wisdom:",skin);
        Label wValue = new Label("8",skin);
        TextButton wUpStat = new TextButton("+",skin);
        TextButton wDownStat = new TextButton("-",skin);

        wisdom.addActor(new Container<Label>(wName).width(90).padLeft(5));
        wisdom.addActor(new Container<Label>(wValue).width(20).left().pad(5));
        wisdom.addActor(new Container<TextButton>(wUpStat).height(30).width(30));
        wisdom.addActor(new Container<TextButton>(wDownStat).height(30).width(30));
        stats.addActor(wisdom);

        //Endurance
        HorizontalGroup endurance = new HorizontalGroup();
        Label eName = new Label("Endurance:",skin);
        final Label eValue = new Label("8",skin);

        TextButton eUpStat = new TextButton("+",skin);
        eUpStat.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(eValue.getText());
                int val = Integer.parseInt(eValue.getText().toString());
                val++;
                eValue.setText(String.valueOf(val));
            }
        });

        TextButton eDownStat = new TextButton("-",skin);
        eDownStat.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(eValue.getText());
                int val = Integer.parseInt(eValue.getText().toString());
                val--;
                eValue.setText(String.valueOf(val));
            }
        });

        endurance.addActor(new Container<Label>(eName).width(90).padLeft(5));
        endurance.addActor(new Container<Label>(eValue).width(20).left().pad(5));
        endurance.addActor(new Container<TextButton>(eUpStat).height(30).width(30));
        endurance.addActor(new Container<TextButton>(eDownStat).height(30).width(30));
        stats.addActor(endurance);

        //Charisma
        HorizontalGroup charisma = new HorizontalGroup();
        Label cName = new Label("Charisma:",skin);
        Label cValue = new Label("8",skin);
        TextButton cUpStat = new TextButton("+",skin);
        TextButton cDownStat = new TextButton("-",skin);

        charisma.addActor(new Container<Label>(cName).width(90).padLeft(5));
        charisma.addActor(new Container<Label>(cValue).width(20).left().pad(5));
        charisma.addActor(new Container<TextButton>(cUpStat).height(30).width(30));
        charisma.addActor(new Container<TextButton>(cDownStat).height(30).width(30));
        stats.addActor(charisma);

        //Luck
        HorizontalGroup luck = new HorizontalGroup();
        Label lName = new Label("Luck:",skin);
        Label lValue = new Label("8",skin);
        TextButton lUpStat = new TextButton("+",skin);
        TextButton lDownStat = new TextButton("-",skin);

        luck.addActor(new Container<Label>(lName).width(90).padLeft(5));
        luck.addActor(new Container<Label>(lValue).width(20).left().pad(5));
        luck.addActor(new Container<TextButton>(lUpStat).height(30).width(30));
        luck.addActor(new Container<TextButton>(lDownStat).height(30).width(30));
        stats.addActor(luck);

        character.add(stats.left()).top().left();

        VerticalGroup prop = new VerticalGroup();

        //HP
        HorizontalGroup hp = new HorizontalGroup();
        Label hName = new Label("HP:",skin);
        Label hValue = new Label("8/20",skin);
        hp.addActor(new Container<Label>(hName).width(90).padLeft(5));
        hp.addActor(new Container<Label>(hValue).width(20).left().pad(5));
        prop.addActor(hp);

        //Mana
        HorizontalGroup mana = new HorizontalGroup();
        Label mName = new Label("Mana:",skin);
        Label mValue = new Label("8/20",skin);
        mana.addActor(new Container<Label>(mName).width(90).padLeft(5));
        mana.addActor(new Container<Label>(mValue).width(20).left().pad(5));
        prop.addActor(mana);

        //Experience
        HorizontalGroup exp = new HorizontalGroup();
        Label expName = new Label("Exp:",skin);
        Label expValue = new Label("8/200",skin);
        exp.addActor(new Container<Label>(expName).width(90).padLeft(5));
        exp.addActor(new Container<Label>(expValue).width(20).left().pad(5));
        prop.addActor(exp);

        //capacity
        HorizontalGroup lory = new HorizontalGroup();
        Label loryName = new Label("Cargo:",skin);
        Label loryValue = new Label("125",skin);
        lory.addActor(new Container<Label>(loryName).width(90).padLeft(5));
        lory.addActor(new Container<Label>(loryValue).width(20).left().pad(5));
        prop.addActor(lory);

        //Points
        HorizontalGroup point = new HorizontalGroup();
        Label ptName = new Label("Point:",skin);
        Label ptValue = new Label("3",skin);
        point.addActor(new Container<Label>(ptName).width(90).padLeft(5));
        point.addActor(new Container<Label>(ptValue).width(20).left().pad(5));
        prop.addActor(point);

        //Level
        HorizontalGroup level = new HorizontalGroup();
        Label lvlName = new Label("Level:",skin);
        Label lvlValue = new Label("1",skin);
        level.addActor(new Container<Label>(lvlName).width(90).padLeft(5));
        level.addActor(new Container<Label>(lvlValue).width(20).left().pad(5));
        prop.addActor(level);

        //Defence
        HorizontalGroup defence = new HorizontalGroup();
        Label dName = new Label("Defence:",skin);
        Label dValue = new Label("1",skin);
        defence.addActor(new Container<Label>(dName).width(90).padLeft(5));
        defence.addActor(new Container<Label>(dValue).width(20).left().pad(5));
        prop.addActor(defence);

        //Melee-Attack
        HorizontalGroup melee = new HorizontalGroup();
        Label mlName = new Label("Melee:",skin);
        Label mlValue = new Label("1-3",skin);
        melee.addActor(new Container<Label>(mlName).width(90).padLeft(5));
        melee.addActor(new Container<Label>(mlValue).width(20).left().pad(5));
        prop.addActor(melee);

        //Range-Attack
        HorizontalGroup range = new HorizontalGroup();
        Label rName = new Label("Range:",skin);
        Label rValue = new Label("0",skin);
        range.addActor(new Container<Label>(rName).width(90).padLeft(5));
        range.addActor(new Container<Label>(rValue).width(20).left().pad(5));
        prop.addActor(range);

        //Magic-Attack
        HorizontalGroup magic = new HorizontalGroup();
        Label mcName = new Label("Magic:",skin);
        Label mcValue = new Label("0",skin);
        magic.addActor(new Container<Label>(mcName).width(90).padLeft(5));
        magic.addActor(new Container<Label>(mcValue).width(20).left().pad(5));
        prop.addActor(magic);

        ScrollPane scroller = new ScrollPane(prop,skin);
        scroller.setForceScroll(false, true);
        scroller.setFadeScrollBars(false);
        scroller.setScrollbarsOnTop(true);
        scroller.setSmoothScrolling(true);
        scroller.getStyle().background = null;

        character.add(scroller).expand().fill();

//        VerticalGroup Resistance = new VerticalGroup();

        addClose(character, skin);
    }

    private void addClose(Window w, Skin skin){
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenHelper.getCharacter().hide();
            }
        });
        w.getButtonTable().add(closeButton).height(w.getPadTop());
    }
}
