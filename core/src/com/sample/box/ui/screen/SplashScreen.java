///*
//package com.sample.box.ui.screen;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.sample.box.DungenGame;
//import com.sample.box.utils.Assets;
//
//public class SplashScreen implements Screen{
//
//    private float minimumShowTime = 3.0f;
//    DungenGame game;
//    Stage stage;
//
//    public SplashScreen(DungenGame game) {
//        this.game = game;
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
//        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
//
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.setViewport( width, height, true );
//
//    }
//
//    @Override
//    public void show() {
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//
//        Image splashImage = new Image(Assets.backgroundTexture);
////		splashImage.addAction(Actions.fadeIn( 2f ));
//        splashImage.addAction( Actions.sequence(Actions.fadeOut(0.001f), Actions.fadeIn(2f), Actions.run(onSplashFinishedRunnable)) );
//
//        stage.addActor(splashImage);
//    }
//
//    Runnable onSplashFinishedRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            game.setScreen(new MenuScreen(game));
//        }
//    };
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
//
//}
//*/
