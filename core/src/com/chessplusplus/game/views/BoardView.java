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
import com.chessplusplus.game.ChessGameImpl;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.utils.FontUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;

/*TODO: Fix weird bug.
   App crashes when any piece is moved to (0, 2) regardless of where it comes from
* */

public class BoardView extends Viewport implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;

    private boolean playerIsWhite = true;
    private int boardWidth;
    private int boardHeight;
    private int squareSize;
    private int spriteSize;
    private int boardYOffset;

    private Board gameBoard;
    private ChessGameImpl game;

    private Texture boardTexture;
    private TextureRegion boardTextureRegion;

    private Texture legalMoveCircle;
    private int circleOffset;

    //Selected piece by user, null if none selected
    private Piece selectedPiece;

    private List<Position> legalMoves;

    public BoardView(SpriteBatch sb) {
        batch = sb;
        legalMoves = new ArrayList<>();
        gameBoard = BoardFactory.standardBoardAndPieces("1", "2");
        game = new ChessGameImpl(gameBoard, "1", "2");
    }

    /**
     * Sets up all relevant attributes and textures for the object.
     */
    @Override
    public void show() {
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        font.getData().setScale(3);

        boardWidth = Gdx.graphics.getWidth();
        squareSize = boardWidth / gameBoard.getWidth();
        spriteSize = squareSize;
        boardHeight = squareSize * gameBoard.getHeight();
        boardYOffset = (Gdx.graphics.getHeight() - boardHeight) / 2;

        makeBoardTexture();
        makeLegalMoveCircleTexture();
    }

    /*
     * Sets texture for the board using the Pixmap class
     * */
    private void makeBoardTexture() {
        Pixmap pixmap = new Pixmap(boardWidth, boardHeight, Pixmap.Format.RGBA8888);

        //Sets colors representing the black and white squares
        Color blackColor = new Color(0.176f, 0.235f, 0.330f, 1);
        Color whiteColor = new Color(0.85f, 0.85f, 0.85f, 1);

        //Starts by coloring entire board with black/white
        pixmap.setColor(playerIsWhite ? blackColor : whiteColor);
        pixmap.fillRectangle(0, 0, boardWidth, boardHeight);

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
        boardTextureRegion = new TextureRegion(boardTexture, 0, 0, boardWidth, boardHeight);
    }

    private void makeLegalMoveCircleTexture() {
        int radius = (int) (squareSize * 0.1);
        Pixmap pixmap = new Pixmap(2 * radius + 1, 2 * radius + 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fillCircle(radius, radius, radius);
        legalMoveCircle = new Texture(pixmap);
        pixmap.dispose();
        circleOffset = squareSize / 2 - radius;
    }


    /**/
    @Override
    public void render(float delta) {
        processUserInput();
        renderBoard();
    }

    private void processUserInput() {
        if (Gdx.input.justTouched()) {

            //Inverts y touch coordinates since libgdx increases y from top to bottom for some cursed reason
            int yTouch = Gdx.graphics.getHeight() - Gdx.input.getY();
            int xTouch = Gdx.input.getX();

            //User touch the board
            if (yTouch > boardYOffset && yTouch < boardYOffset + boardHeight) {

                //yTouch relative to the board
                yTouch -= boardYOffset;
                //Convert from pixel coordinates to board coordinates
                Position actionPos = Position.pos(xTouch / squareSize, yTouch / squareSize);
                if (!game.getBoard().squareIsEmpty(actionPos)) {
                    Piece pieceTemp = game.getBoard().getPiece(actionPos);
                    //The selected piece equals previously selected piece
                    if (selectedPiece != null && selectedPiece.equals(pieceTemp)) {
                        selectedPiece = null;
                    } else {
                        selectedPiece = pieceTemp;
                    }
                } else if (selectedPiece != null) {
                    Turn.Action action = new Turn.Action(selectedPiece, Turn.ActionType.MOVEMENT,
                            selectedPiece.getPosition(), actionPos);
                    System.out.println(action);
                    List<Turn.Action> actions = new ArrayList<>();
                    actions.add(action);
                    Turn turn = new Turn("1", actions);
                    game.submitTurn(turn);
                    selectedPiece = null;
                }
            } else {
                selectedPiece = null;
            }
        }
    }

    /*Renders the board texture as well as all the pieces by calling the renderPiece method*/
    private void renderBoard() {
        batch.draw(boardTextureRegion, 0, boardYOffset);
        for (Piece piece : gameBoard.getAllPieces()) {
            renderPiece(piece.getTexture(), piece.getPosition().getX(), piece.getPosition().getY(), 1);
        }

        if (selectedPiece != null) {
            for (Turn turn : selectedPiece.getLegalTurns(game.getBoard())) {
                for (Turn.Action action : turn.actions) {
                    legalMoves.add(action.actionPos);
                    if (action.actionType == Turn.ActionType.MOVEMENT) {
                        batch.draw(legalMoveCircle, action.actionPos.getX() * squareSize + circleOffset,
                                action.actionPos.getY() * squareSize + boardYOffset + circleOffset);
                    }
                }
            }
        }
    }

    /**
     * Renders a single chess piece on the board.
     *
     * @param x     and y params represents coordinates on the board, on which the piece will be rendered on, starting from index 0
     *              (0, 1, 2, ....)
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
        for (Piece piece : gameBoard.getAllPieces()) {
            piece.getTexture().dispose();
        }
        boardTexture.dispose();
        legalMoveCircle.dispose();
    }
}
