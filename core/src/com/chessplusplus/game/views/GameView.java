package com.chessplusplus.game.views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chessplusplus.ChessPlusPlus;

public class GameView extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private BoardView boardView;

    public GameView(ChessPlusPlus c){
        chessPlusPlus = c;
        boardView = new BoardView(c.getBatch());
    }

    @Override
    public void create() {
        boardView.show();
    }

    @Override
    public void render() {
        boardView.render(0);
    }

    @Override
    public void dispose() {
        boardView.dispose();
    }
}
