package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartMenu extends ApplicationAdapter {

    private Stage stage;
    private Skin skin;

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        skin.getFont("default-font").getData().setScale(3,3);

        final TextButton playGameButton = new TextButton("Play Game", skin, "default");
        playGameButton.setSize(600, 300);
        playGameButton.setPosition(stage.getWidth()/2 - 625, stage.getHeight()/2 + 25);

        final TextButton tutorialButton = new TextButton("Tutorial", skin, "default");
        tutorialButton.setSize(600, 300);
        tutorialButton.setPosition(stage.getWidth()/2 + 25, stage.getHeight()/2 +25);

        final TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.setSize(600, 300);
        settingsButton.setPosition(stage.getWidth()/2 - 625, stage.getHeight()/2 - 325);

        final TextButton manualButton = new TextButton("Manual", skin, "default");
        manualButton.setSize(600, 300);
        manualButton.setPosition(stage.getWidth()/2 + 25, stage.getHeight()/2 - 325);

        playGameButton.addListener(new ClickListener() {

        });

        stage.addActor(playGameButton);
        stage.addActor(tutorialButton);
        stage.addActor(settingsButton);
        stage.addActor(manualButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
