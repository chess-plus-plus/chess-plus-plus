package com.chessplusplus.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chessplusplus.ChessPlusPlus;
import com.chessplusplus.FirebaseController;
import com.chessplusplus.game.Board;
import com.chessplusplus.game.BoardFactory;
import com.chessplusplus.game.ChessGameImpl;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceColor;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.utils.FontUtils;

/*TODO: Fix weird bug.
   App crashes when any piece is moved to (0, 2) regardless of where it comes from. If the pawn
   in this very column has moved passed the coordinate, the bug doesnt occur anymore
* */

public class BoardView extends Viewport implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private FirebaseController FBC;

    private boolean playerIsWhite;
    private int boardWidth;
    private int boardHeight;
    private int squareSize;
    private int spriteSize;
    private int boardYOffset;

    private Board gameBoard;
    private ChessGameImpl game;
    private String gameID;

    private Texture boardTexture;
    private TextureRegion boardTextureRegion;

    private Texture legalMoveCircle;
    private int circleOffset;

    private Texture strikeOptionTexture;

    private Texture xpBarOutlineTexture;
    private Texture xpBarProgressTexture;
    private int xpBarWidth;
    private int xpBarXPos;

    //Selected piece by user, null if none selected
    private Piece selectedPiece;

    public BoardView(ChessPlusPlus c, String gameID, String playerID) {
        batch = c.getBatch();
        gameBoard = BoardFactory.standardBoardAndPieces("1", "2");
        game = new ChessGameImpl(gameBoard, c.getFBC(), gameID, "1", "2");
        game.setPlayer(playerID);
        this.gameID = gameID;
        this.FBC = c.getFBC();

        playerIsWhite = game.getPlayerColor(game.getPlayerID()) == PieceColor.WHITE;
        System.out.println("Is white: " + playerIsWhite);
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

        //Finds color of the piece based on player id and passes it to the pieces
        for (Piece piece : game.getBoard().getAllPieces()) {
            String playerId = piece.getPlayerId();
            String filePath = String.format("pieces/%s/", game.getPlayerColor(playerId));
            piece.setTexture(filePath);
        }

        makeBoardTexture();
        makeLegalMoveTextures();
        makeProgressBarTextures();
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

    private void makeLegalMoveTextures() {
        //Makes circle texture
        int radius = (int) (squareSize * 0.1);
        Pixmap pixmap = new Pixmap(2 * radius + 1, 2 * radius + 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fillCircle(radius, radius, radius);
        legalMoveCircle = new Texture(pixmap);
        pixmap.dispose();
        circleOffset = squareSize / 2 - radius;

        //Makes strike rectangle (not filled)
        pixmap = new Pixmap(squareSize, squareSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.FIREBRICK);
        for (int i = 0; i < 10; i++) {
            pixmap.drawRectangle(i, i, squareSize - i * 2, squareSize - i * 2);
        }
        strikeOptionTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    private void makeProgressBarTextures() {
        xpBarWidth = (int) (Gdx.graphics.getWidth() * 0.4);
        Pixmap pixmap = new Pixmap(xpBarWidth, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        //Draws rectangle width 4 px line width
        for (int i = 0; i < 4; i++) {
            pixmap.drawRectangle(i, i, xpBarWidth - i * 2, 100 - i * 2);
        }
        xpBarOutlineTexture = new Texture(pixmap);
        pixmap.dispose();
        pixmap = new Pixmap(xpBarWidth, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CYAN);
        pixmap.fillRectangle(0, 0, xpBarWidth, 100);
        xpBarProgressTexture = new Texture(pixmap);
        pixmap.dispose();

        xpBarXPos = Gdx.graphics.getWidth() / 2 - xpBarWidth / 2;
    }


    /**/
    @Override
    public void render(float delta) {
        Turn newTurn = this.FBC.getNewTurnIfAvailable();
        if (newTurn != null)
            game.submitTurn(newTurn, true);
        if (game.isPlayerTurn())
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
                Position actionPos = Position.pos(xTouch / squareSize, convertY(yTouch / squareSize));
                game.processUserInput(this, actionPos);
            } else {
                selectedPiece = null;
            }
            //Prevents user from getting possible moves by clicking opponent pieces
            if (!game.isFriendlyPiece(selectedPiece)) {
                selectedPiece = null;
            }
        }
    }

    /*Renders the board texture as well as all the pieces by calling the renderPiece method*/
    private void renderBoard() {
        //Signals which players turn it is
        String whichTurn = "Waiting for opponent...";
        if (game.isPlayerTurn()) {
            whichTurn = "Your turn";
        }
        font.setColor(Color.WHITE);
        font.getData().setScale(6);
        font.draw(batch, whichTurn, (float) Gdx.graphics.getWidth() / 2 - FontUtils.getWidthOfFontText(whichTurn, font) / 2,
                Gdx.graphics.getHeight() - 200);
        //Draws the board
        batch.draw(boardTextureRegion, 0, boardYOffset);

        //Renders each valid move of selected piece
        if (selectedPiece != null) {
            for (Turn turn : selectedPiece.getLegalTurns(game.getBoard())) {
                for (Turn.Action action : turn.actions) {
                    if (action.actionType == Turn.ActionType.MOVEMENT || action.actionType == Turn.ActionType.DESTRUCTION) {
                        batch.draw(legalMoveCircle, action.actionPos.getX() * squareSize + circleOffset,
                                convertY(action.actionPos.getY()) * squareSize + boardYOffset + circleOffset);
                    } else if (action.actionType == Turn.ActionType.STRIKE) {
                        batch.draw(strikeOptionTexture, action.actionPos.getX() * squareSize,
                                convertY(action.actionPos.getY()) * squareSize + boardYOffset);
                    }
                }
            }
        }
        font.setColor(Color.CYAN);
        font.getData().setScale(3);
        //Renders the pieces
        for (Piece piece : gameBoard.getAllPieces()) {
            renderPiece(piece.getTexture(), piece.getPosition().getX(), convertY(piece.getPosition().getY()), 1);
        }

        //The XP bar of the selected piece
        if (selectedPiece != null) {
            batch.draw(xpBarProgressTexture, xpBarXPos, 200, (int) (xpBarWidth * 0.5), 100);
            batch.draw(xpBarOutlineTexture, xpBarXPos, 200, xpBarWidth, 100);
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
        strikeOptionTexture.dispose();
        xpBarOutlineTexture.dispose();
        xpBarProgressTexture.dispose();
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    /**
     * Returns y coordinate that is relative to the player or absolute to the game. Very important to
     * convert y-coordinates when sending out y-coordinate and when receiving.
     *
     * @param y y-coordinate relative to the player
     * @return correct y to use for visualization or correct y to send out to controller
     */
    private int convertY(int y) {
        return game.playerIsBottom() ? y : gameBoard.getHeight() - y - 1;
    }
}
