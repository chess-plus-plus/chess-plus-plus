package com.chessplusplus.game.views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chessplusplus.ChessPlusPlus;

public class StartMenuView extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private Stage stage;
    private Skin skin;

    public StartMenuView(ChessPlusPlus c){
        chessPlusPlus = c;
    }

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        skin.getFont("default-font").getData().setScale(3,3);

        final TextButton connectedButton = new TextButton("", skin, "default");
        connectedButton.setDisabled(true);
        connectedButton.align(Align.center);
        connectedButton.setWidth(stage.getWidth());
        if (chessPlusPlus.getConnected()){
            connectedButton.setText("Connected");
            connectedButton.setColor(Color.GREEN);
        } else {
            connectedButton.setText("Not Connected");
            connectedButton.setColor(Color.RED);
        }

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.align(Align.center);
        table.setPosition(0, 0);

        final TextField titleField = new TextField("Start Menu", skin, "default");
        titleField.setDisabled(true);
        titleField.setAlignment(Align.center);

        final TextButton playGameButton = new TextButton("Play Game", skin, "default");
        playGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new PlayGameMenuView(chessPlusPlus));
            }
        });

        final TextButton tutorialButton = new TextButton("Tutorial", skin, "default");
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new TutorialView(chessPlusPlus));
            }
        });

        final TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new SettingsView(chessPlusPlus));
            }
        });

        final TextButton manualButton = new TextButton("Manual", skin, "default");
        manualButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new ManualView(chessPlusPlus));
            }
        });

        table.add(titleField).padBottom(50).width(stage.getWidth()/2);
        table.row();
        table.add(playGameButton).padBottom(30).width(stage.getWidth()/2);
        table.row();
        table.add(tutorialButton).padBottom(30).width(stage.getWidth()/2);
        table.row();
        table.add(settingsButton).padBottom(30).width(stage.getWidth()/2);
        table.row();
        table.add(manualButton).padBottom(30).width(stage.getWidth()/2);

        stage.addActor(connectedButton);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
