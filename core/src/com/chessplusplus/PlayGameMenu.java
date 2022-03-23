package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayGameMenu extends ApplicationAdapter {

    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        skin.getFont("default-font").getData().setScale(3,3);

        final TextButton joinGameButton = new TextButton("Join Game", skin, "default");
        joinGameButton.setSize(600, 300);
        joinGameButton.setPosition(stage.getWidth()/2 - 300, stage.getHeight()/2 + 25);

        final TextButton hostGameButton = new TextButton("Host Game", skin, "default");
        hostGameButton.setSize(600, 300);
        hostGameButton.setPosition(stage.getWidth()/2 - 300, stage.getHeight()/2 - 325);

        stage.addActor(joinGameButton);
        stage.addActor(hostGameButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
