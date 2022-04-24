package com.chessplusplus.game;

import com.chessplusplus.ChessPlusPlus;
import com.chessplusplus.FirebaseController;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.views.BoardView;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Chess Game.
 */
public class ChessGameController {

    private String player1ID;
    private String player2ID;
    private ChessGame chessGame;
    private Map<String, PieceColor> playerColorMap = new HashMap<>();
    private String playerID; //The player that this code will run on.
    private String gameID;
    private boolean offlineTesting;
    private FirebaseController FBC;

    public ChessGameController(ChessPlusPlus c, String gameID, String playerID, boolean offlineTesting) {
        player1ID = "1";
        player2ID = "2";
        this.playerID = playerID;
        this.gameID = gameID;
        this.offlineTesting = offlineTesting;

        FBC = c.getFBC();

        Board chessBoard = BoardFactory.standardBoardAndPieces(player1ID, player2ID);
        chessGame = new ChessGameImpl(chessBoard, player1ID, player2ID,
                RPGConfig.DEFAULT_MOVEMENT_XP, RPGConfig.DEFAULT_STRIKE_XP,
                LevelUpEffectFactory.createDefaultRPGRules(chessBoard.getHeight() - 1));

        playerColorMap.put(player1ID, PieceColor.WHITE);
        playerColorMap.put(player2ID, PieceColor.WHITE);
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    private Board getBoard() {
        return chessGame.getBoard();
    }

    public int getBoardWidth() {
        return chessGame.getBoard().getWidth();
    }

    public int getBoardHeight() {
        return chessGame.getBoard().getHeight();
    }

    /**
     * Note to FelixB: When we for example want to move a queen to an opponent the following actions will
     * be sent: STRIKE, DESTRUCTION, MOVEMENT
     * <p>
     * Processes the movement input from the boardview
     *
     * @param boardView BoardView-Screen that renders game to user
     * @param actionPos Coordinates on the board to be processed
     */
    public void processUserInput(BoardView boardView, Position actionPos) {
        if (!this.getBoard().squareIsEmpty(actionPos) && boardView.getSelectedPiece() == null) {
            Piece pieceTemp = this.getBoard().getPiece(actionPos);
            //The selected piece equals previously selected piece
            if (boardView.getSelectedPiece() != null && boardView.getSelectedPiece().equals(pieceTemp)) {
                boardView.setSelectedPiece(null);
            } else {
                boardView.setSelectedPiece(pieceTemp);
            }
        } else if (boardView.getSelectedPiece() != null) {
            Turn turnToSubmit = null;
            for (Turn turn : boardView.getSelectedPiece().getLegalTurns(this.getBoard())) {
                for (Turn.Action action : turn.actions) {
                    //Finds the legal turn corresponding to the action position and saves the turn
                    if (action.actionPos.equals(actionPos)) {
                        turnToSubmit = turn;
                        break;
                    }
                }
            }
            if (turnToSubmit != null)
                //Submits saved turn
                submitTurn(turnToSubmit, false);
            boardView.setSelectedPiece(null);
        }
    }

    public void submitTurn(Turn turn, boolean fromOnline) {
        if (fromOnline && turn.playerId.equals(this.playerID)) {
            return;
        }
        if (!chessGame.turnIsLegal(turn) && !fromOnline) {
        } else {
            chessGame.submitTurn(turn, fromOnline);
            if (!fromOnline && !offlineTesting) {
                FBC.sendTurn(gameID, turn);
            }
        }
    }

    /**
     * Gets the color belonging to the player.
     *
     * @param playerId Id of player
     * @return Color belonging to player
     * @throws IllegalArgumentException when playerId is not valid
     */
    public PieceColor getPlayerColor(String playerId) throws IllegalArgumentException {
        if (playerColorMap.get(playerId) == null) {
            throw new IllegalArgumentException("No such player id");
        }
        return playerColorMap.get(playerId);
    }

    public String getPlayerID() {
        return playerID;
    }

    /**
     * Is the players turn
     */
    public boolean isPlayerTurn() {
        return chessGame.getCurrentPlayer().equals(playerID);
    }

    /**
     * Determines if the player belongs to the bottom of the board (If the player belongs to y=0 in
     * other words)
     */
    public boolean playerIsBottom() {
        return playerID.equals(player1ID);
    }

    /**
     * Determines if a piece is friendly to the player running the code
     *
     * @param piece Piece to be evaluated
     * @return the result as boolean
     */
    public boolean isFriendlyPiece(Piece piece) {
        if (piece == null) return false;
        return piece.getPlayerId().equals(playerID);
    }

    public boolean getOfflineTesting() {
        return offlineTesting;
    }

}
