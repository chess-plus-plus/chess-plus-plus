package com.chessplusplus.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chessplusplus.ApplicationController;

public class JoinGameMenuView extends ApplicationAdapter {

    private ApplicationController applicationController;
    private Stage stage;
    private Skin skin;
    private TextButton conBut;

    public JoinGameMenuView(ApplicationController c){
        applicationController = c;
    }

    @Override
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

        final Dialog wrongPinDialog = new Dialog("Game does not exist", skin, "default");

        final TextField titleField = new TextField("Input game pin:", skin, "default");
        titleField.setDisabled(true);
        titleField.setAlignment(Align.center);

        final TextField gamePinInput = new TextField("", skin, "default");

        final TextButton startGameButton = new TextButton("Start Game", skin, "default");
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                String id = gamePinInput.getText();
                boolean joined = applicationController.joinGame(id);
                if (joined) {
                    applicationController.setScreen(new GameView(applicationController, id, "2", false));
                }
                else {
                    wrongPinDialog.show(stage);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            wrongPinDialog.hide();
                        }
                    }, 2);
                }
            }
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                applicationController.setScreen(new PlayGameMenuView(applicationController));
            }
        });

        table.add(titleField).padBottom(50).width((float) (stage.getWidth()/1.7));
        table.row();
        table.add(gamePinInput).padBottom(30).width(stage.getWidth()/2);
        table.row();
        table.add(startGameButton).padBottom(30).width(stage.getWidth()/2);
        table.row();
        table.add(backButton).width(stage.getWidth()/2);

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
