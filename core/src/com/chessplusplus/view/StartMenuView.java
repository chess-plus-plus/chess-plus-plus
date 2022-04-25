package com.chessplusplus.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chessplusplus.ApplicationController;

public class StartMenuView extends ApplicationAdapter {

    private ApplicationController applicationController;
    private Stage stage;
    private Skin skin;
    private TextButton conBut;

    public StartMenuView(ApplicationController c){
        applicationController = c;
    }

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        skin.getFont("default-font").getData().setScale(3,3);

        final TextButton connectedButton = new TextButton("", skin, "default");
        connectedButton.setDisabled(true);
        connectedButton.align(Align.center);
        connectedButton.setWidth(stage.getWidth());
        conBut = connectedButton;

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.align(Align.center);
        table.setPosition(0, 0);


        final Image titleLogo = new Image(new Texture(Gdx.files.internal("title.png")));
        final float titleScale = 0.6f;
        final float titleWidth = 1600 * titleScale;
        final float titleHeight = 500 * titleScale;


        final TextField titleField = new TextField("Start Menu", skin, "default");
        titleField.setDisabled(true);
        titleField.setAlignment(Align.center);

        final TextButton playGameButton = new TextButton("Play Game", skin, "default");
        playGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                applicationController.setScreen(new PlayGameMenuView(applicationController));
            }
        });

        final TextButton tutorialButton = new TextButton("Tutorial", skin, "default");
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                applicationController.setScreen(new TutorialView(applicationController));
            }
        });

        final TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                applicationController.setScreen(new SettingsView(applicationController));
            }
        });

        final TextButton manualButton = new TextButton("Manual", skin, "default");
        manualButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                applicationController.setScreen(new ManualView(applicationController));
            }
        });



        table.add(titleLogo).padBottom(100).width(titleWidth).height(titleHeight);
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

        if (applicationController.isConnected()){
            conBut.setText("Connected");
            conBut.setColor(Color.GREEN);
        } else {
            conBut.setText("Not Connected");
            conBut.setColor(Color.RED);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(new Texture(Gdx.files.internal("background.png")), 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();

        stage.draw();
    }
}
