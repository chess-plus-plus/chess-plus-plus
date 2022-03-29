package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

public class PlayGameMenu extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private Stage stage;
    private Skin skin;

    public PlayGameMenu(ChessPlusPlus c){
        chessPlusPlus = c;
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        skin.getFont("default-font").getData().setScale(3,3);

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.align(Align.center);
        table.setPosition(0, 0);

        final TextField titleField = new TextField("Play Game", skin, "default");
        titleField.setDisabled(true);
        titleField.setAlignment(Align.center);

        final TextButton joinGameButton = new TextButton("Join Game", skin, "default");
        joinGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new JoinGameMenu(chessPlusPlus));
            }
        });

        final TextButton hostGameButton = new TextButton("Host Game", skin, "default");
        hostGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new HostGameMenu(chessPlusPlus));
            }
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new StartMenu(chessPlusPlus));
            }
        });

        table.add(titleField).padBottom(50).width(stage.getWidth()/3);
        table.row();
        table.add(joinGameButton).padBottom(30).width(stage.getWidth()/3);
        table.row();
        table.add(hostGameButton).padBottom(30).width(stage.getWidth()/3);
        table.row();
        table.add(backButton).width(stage.getWidth()/3);

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
