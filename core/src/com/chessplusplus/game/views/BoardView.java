package com.chessplusplus.game.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BoardView implements Screen {

    private SpriteBatch batch;

    private int boardDimension = 8;
    private int boardSize = 600;
    private int squareSize = boardSize / 8;

    public BoardView(SpriteBatch sb) {
        batch = sb;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Pixmap pixmap = new Pixmap( boardSize, boardSize, Pixmap.Format.RGBA8888 );
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0,0,boardSize, boardSize);

        pixmap.setColor(Color.WHITE);

        int xStart = 0;
        for (int y = 0; y < boardDimension; y++) {
            xStart = (y % 2 == 0 ? 0 : 1);
            for (int x = xStart; x < boardDimension; x += 2) {
                pixmap.fillRectangle(x * squareSize,y * squareSize, squareSize, squareSize);
            }
        }

        Texture boardTexture = new Texture( pixmap );
        pixmap.dispose();
        TextureRegion boardTextureRegion = new TextureRegion(boardTexture,0,0,boardSize,boardSize);
        batch.draw(boardTextureRegion,0,0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
