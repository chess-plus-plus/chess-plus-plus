package com.chessplusplus.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chessplusplus.game.utils.FontUtils;

import java.util.ArrayList;

public class BoardView extends Viewport implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;

    private int boardDimension = 8;
    private boolean playerIsWhite = true;
    private int boardSize;
    private int squareSize;
    private int spriteSize;
    private int boardYOffset;

    ArrayList<Texture> textures = new ArrayList<>();
    private Texture boardTexture;
    private TextureRegion boardTextureRegion;

    public BoardView(SpriteBatch sb) {
        batch = sb;
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
        squareSize = boardSize / boardDimension;
        spriteSize = squareSize;
        boardYOffset = (Gdx.graphics.getHeight() - boardSize) / 2;

        textures.add(new Texture("pieces/white/king.png"));
        textures.add(new Texture("pieces/white/queen.png"));
        textures.add(new Texture("pieces/white/bishop.png"));
        textures.add(new Texture("pieces/white/knight.png"));
        textures.add(new Texture("pieces/white/rook.png"));
        textures.add(new Texture("pieces/white/pawn.png"));

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
        for (int y = 0; y < boardDimension; y++) {
            xStart = (y % 2 == 0 ? 0 : 1);
            for (int x = xStart; x < boardDimension; x += 2) {
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

        renderPiece(textures.get(0), 4, 0, 0);
        renderPiece(textures.get(1), 3, 0, 2);
        renderPiece(textures.get(2), 2, 0, 1);
        renderPiece(textures.get(2), 5, 0, 0);
        renderPiece(textures.get(3), 1, 0, 1);
        renderPiece(textures.get(3), 6, 0, 0);
        renderPiece(textures.get(4), 0, 0, 0);
        renderPiece(textures.get(4), 7, 0, 0);

        for (int i = 0; i < boardDimension; i++) {
            renderPiece(textures.get(5), i, 1, 0);
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
        for (Texture texture : textures) {
            texture.dispose();
        }
        boardTexture.dispose();
    }
}
