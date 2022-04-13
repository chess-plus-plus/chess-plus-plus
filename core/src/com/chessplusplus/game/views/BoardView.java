package com.chessplusplus.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chessplusplus.game.Board;
import com.chessplusplus.game.BoardFactory;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.utils.FontUtils;

import java.util.ArrayList;
import java.util.Vector;

public class BoardView extends Viewport implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;

    private boolean playerIsWhite = true;
    private int boardSize;
    private int squareSize;
    private int spriteSize;
    private int boardYOffset;

    private Board gameBoard;

    private Texture boardTexture;
    private TextureRegion boardTextureRegion;

    public BoardView(SpriteBatch sb) {
        batch = sb;
        gameBoard = BoardFactory.standardBoardAndPieces("1", "2");
        for (Piece piece: gameBoard.getAllPieces()) {
            System.out.println(piece);
        }
    }

    /**
    * Sets up all relevant attributes and textures for the object.
    * */
    @Override
    public void show() {
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        font.getData().setScale(3);

        boardSize = Gdx.graphics.getWidth();
        squareSize = boardSize / gameBoard.getWidth();
        spriteSize = squareSize;
        boardYOffset = (Gdx.graphics.getHeight() - boardSize) / 2;

        makeBoardTexture();
    }

    /*
     * Sets texture for the board using the Pixmap class
     * */
    private void makeBoardTexture() {
        Pixmap pixmap = new Pixmap(boardSize, boardSize, Pixmap.Format.RGBA8888);

        //Sets colors representing the black and white squares
        Color blackColor = new Color(0.176f, 0.235f, 0.330f, 1);
        Color whiteColor = new Color(0.85f, 0.85f, 0.85f, 1);

        //Starts by coloring entire board with black/white
        pixmap.setColor(playerIsWhite ? blackColor : whiteColor);
        pixmap.fillRectangle(0, 0, boardSize, boardSize);

        //Loops through the squares and colors squares with opposite color of initial color
        pixmap.setColor(playerIsWhite ? whiteColor : blackColor);
        int xStart;
        for (int y = 0; y < gameBoard.getHeight(); y++) {
            xStart = (y % 2 == 0 ? 0 : 1);
            for (int x = xStart; x < gameBoard.getWidth(); x += 2) {
                pixmap.fillRectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            }
        }

        boardTexture = new Texture(pixmap);
        pixmap.dispose();
        boardTextureRegion = new TextureRegion(boardTexture, 0, 0, boardSize, boardSize);
    }

    /**/
    @Override
    public void render(float delta) {
        renderBoard();
    }

    /*Renders the board texture as well as all the pieces by calling the renderPiece method*/
    private void renderBoard() {
        batch.draw(boardTextureRegion, 0, boardYOffset);

        for (Piece piece : gameBoard.getAllPieces()) {
            renderPiece(piece.getTexture(), piece.getPosition().getX(), piece.getPosition().getY(), 1);
        }
    }

    /**
     * Renders a single chess piece on the board.
     * @param x and y params represents coordinates on the board, on which the piece will be rendered on, starting from index 0
     * (0, 1, 2, ....)
     * @param level is the xp level of the piece. Accepted values are 0, 1, 2
     */
    private void renderPiece(Texture sprite, int x, int y, int level) {
        float xOffset = squareSize - spriteSize;
        xOffset /= 2;
        float xPos = x * squareSize + xOffset;
        float yPos = y * squareSize + boardYOffset;
        batch.draw(sprite, x * squareSize + xOffset, y * squareSize + boardYOffset, spriteSize, spriteSize);

        String toBeWritten;
        if (level >= 2) {
            toBeWritten = "MAX";
        } else {
            toBeWritten = String.valueOf(level + 1);
        }
        font.draw(batch, toBeWritten, xPos + 3, yPos + 3 + FontUtils.getHeightOfFontText(toBeWritten, font));
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
        for (Piece piece:gameBoard.getAllPieces()) {
            piece.getTexture().dispose();
        }
        boardTexture.dispose();
    }
}
