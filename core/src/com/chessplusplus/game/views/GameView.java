package com.chessplusplus.game.views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chessplusplus.ChessPlusPlus;
import com.chessplusplus.game.utils.FontUtils;

public class GameView extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private BoardView boardView;
    private String gameID;

    private BitmapFont font;
    private SpriteBatch batch;


    public GameView(ChessPlusPlus c, String id){
        chessPlusPlus = c;
        gameID = id;
        batch = c.getBatch();
        boardView = new BoardView(batch);
    }

    @Override
    public void create() {
        font = new BitmapFont();
        font.getData().setScale(3);

        boardView.show();
    }

    @Override
    public void render() {
        boardView.render(0);
        font.draw(batch, gameID, (Gdx.graphics.getWidth() / 2) - (FontUtils.getWidthOfFontText(gameID, font) / 2), Gdx.graphics.getHeight() - 50);
    }

    @Override
    public void dispose() {
        boardView.dispose();
    }
}
