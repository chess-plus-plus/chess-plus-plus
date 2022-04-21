package com.chessplusplus.game.views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chessplusplus.ChessPlusPlus;
import com.chessplusplus.game.utils.FontUtils;

public class GameView extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private BoardView boardView;
    private String gameID;

    private BitmapFont font;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;
    private TextButton conBut;

    public GameView(ChessPlusPlus c, String id){
        chessPlusPlus = c;
        gameID = id;
        batch = c.getBatch();
        boardView = new BoardView(c, gameID);
    }

    @Override
    public void create() {
        font = new BitmapFont();
        font.getData().setScale(3);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        skin.getFont("default-font").getData().setScale(3,3);

        boardView.show();


        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.align(Align.center);
        table.align(Align.bottom);
        table.setPosition(0, 0);

        final TextButton manualButton = new TextButton("Manual", skin, "default");
        manualButton.align(Align.center);
        GameView gW = this;
        manualButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                chessPlusPlus.setScreen(new ManualView(chessPlusPlus, gW));
            }
        });

        final TextButton connectedButton = new TextButton("", skin, "default");
        connectedButton.setDisabled(true);
        connectedButton.align(Align.center);
        connectedButton.setWidth(stage.getWidth());
        conBut = connectedButton;

        table.add(manualButton).padBottom(50);
        table.row();
        table.add(connectedButton);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        boardView.render(0);
        String toRender = "Game ID: " + gameID;

        if (chessPlusPlus.isConnected()){
            conBut.setText("Connected");
            conBut.setColor(Color.GREEN);
        } else {
            conBut.setText("Not Connected");
            conBut.setColor(Color.RED);
        }
        font.draw(batch, toRender, (Gdx.graphics.getWidth() / 2) - (FontUtils.getWidthOfFontText(toRender, font) / 2), Gdx.graphics.getHeight() - 50);
        stage.draw();
    }

    @Override
    public void dispose() {
        boardView.dispose();
    }
}
