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
    private int squareSize = boardSize / boardDimension;
    private boolean playerIsWhite = true;
    private int spriteSize = 70;

    public BoardView(SpriteBatch sb) {
        batch = sb;
    }

    @Override
    public void show() {

    }

    /**/
    @Override
    public void render(float delta) {
        renderBoard();
    }

    private void renderBoard() {
        Pixmap pixmap = new Pixmap( boardSize, boardSize, Pixmap.Format.RGBA8888 );

        //Sets colors representing the black and white squares
        Color blackColor = new Color(0.176f, 0.235f, 0.330f, 1);
        Color whiteColor= new Color(0.85f, 0.85f, 0.85f, 1);

        //Starts by coloring entire board with black/white
        pixmap.setColor(playerIsWhite ? blackColor : whiteColor);
        pixmap.fillRectangle(0,0,boardSize, boardSize);

        //Loops through the squares and colors squares with opposite color of initial color
        pixmap.setColor(playerIsWhite ? whiteColor : blackColor);
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

        renderPiece(new TextureRegion(new Texture("pieces/white/king.png")), 4, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/queen.png")), 3, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/bishop.png")), 2, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/bishop.png")), 5, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/knight.png")), 1, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/knight.png")), 6, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/rook.png")), 0, 0);
        renderPiece(new TextureRegion(new Texture("pieces/white/rook.png")), 7, 0);

        for (int i = 0; i < boardDimension; i++) {
            renderPiece(new TextureRegion(new Texture("pieces/white/pawn.png")), i, 1);
        }

    }

    /*Renders a single piece.
    * x and y params represents coordinates on the board, on which the piece will be rendered on, starting from index 0
    * (0, 1, 2, ....)*/
    private void renderPiece(TextureRegion sprite, int x, int y) {
        float xOffset = squareSize - spriteSize;
        xOffset /= 2;
        batch.draw(sprite, x * squareSize + xOffset, y*squareSize, spriteSize, spriteSize);
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
