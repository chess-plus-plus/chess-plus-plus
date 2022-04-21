package com.chessplusplus.game;

import com.chessplusplus.FirebaseController;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.views.BoardView;
import com.chessplusplus.game.views.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// Ad hoc class used purely to test out ECS
// To "run" the demo, just click a button or on the application window,
// and you should see the position of the piece update each time.
//  TODO: Game needs a module restructuring
public class ChessGameImpl implements ChessGame {

    private Board gameBoard;
    private List<Turn> gameTurnHistory = new ArrayList<>();
    private FirebaseController FBC;

    private final String player1Id;
    private final String player2Id;
    private String currentPlayerId;

    private String playerID; //The player that this code will run on.
    private String gameID;
    private HashMap<String, PieceColor> playerIdToPieceColor = new HashMap<>();

    private HashMap<Turn, Piece> legalTurnsToPieceMap = new HashMap<>();

    public ChessGameImpl(Board gameBoard, FirebaseController FBC, String gameID, String player1Id, String player2Id) {
        this.gameBoard = gameBoard;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        currentPlayerId = player1Id;
        this.playerID = player2Id;
        this.gameID = gameID;
        this.FBC = FBC;

        calculateAllLegalTurns();

        playerIdToPieceColor.put(player1Id, PieceColor.WHITE);
        playerIdToPieceColor.put(player2Id, PieceColor.BLACK);

    }

    @Override
    public Board getBoard() {
        return gameBoard;
    }

    @Override
    public String getCurrentPlayer() {
        return currentPlayerId;
    }

    @Override
    public boolean submitTurn(Turn turn, boolean fromOnline) {
        if (fromOnline && turn.playerId.equals(this.playerID))
            return false;
        if (!legalTurnsToPieceMap.containsKey(turn) && !fromOnline) {
            return false;
        } else {
            updateGame(turn);
            if (!fromOnline)
                FBC.sendTurn(gameID, turn);
            return true;
        }
    }

    /**
     * Updates game state:
     * 1: Add turn to turn history.
     * 2: Update board.
     * 3: Update currentPlayerId;
     * @param turn A valid turn.
     */
    private void updateGame(Turn turn) {
        // 1
        gameTurnHistory.add(turn);
        System.out.println("New turn");
        // 2
        for (Turn.Action action : turn.actions) {
            System.out.println(action);
            switch (action.actionType) {
                case STRIKE:
                    //TODO: Add XP to striker.
                    //gameBoard.removePiece(action.piece);
                    gameBoard.removePiece(gameBoard.getPiece(action.actionPos));
                    break;
                case MOVEMENT:
                    gameBoard.getPiece(action.piece.getPosition()).moveTo(action.actionPos);
                    break;
                case DESTRUCTION:
                    gameBoard.removePiece(gameBoard.getPiece(action.piece.getPosition()));
                    break;
                case CREATION:
                    //TODO
                    break;
            }
        }
        gameBoard.updateBoard();
        calculateAllLegalTurns();

        // 3, a bit hacky, but I'm tired and it should work.
        currentPlayerId = currentPlayerId.equals(player1Id) ? player2Id : player1Id;
    }

    /**
     * Refreshes the legal turns all pieces can take.
     */
    private void calculateAllLegalTurns() {
        List<Piece> pieces = gameBoard.getAllPieces();
        for (Piece piece : pieces) {
            List<Turn> turns = piece.getLegalTurns(gameBoard);
            for (Turn turn : turns) {
                legalTurnsToPieceMap.put(turn, piece);
            }
        }
    }

    @Override
    public List<Turn> getTurns() {
        return gameTurnHistory;
    }

    @Override
    public boolean gameIsOver() {
        //TODO
        return false;
        /*
         * Idea:
         * 1: Check if either king is in check.
         * 2: If a king is in check, check if it has any legal moves that let's it escape check.
         */
    }

    /**
     * Gets the color belonging to the player.
     * @param playerId Id of player
     * @throws IllegalArgumentException when playerId is not valid
     * @return Color belonging to player*/
    public PieceColor getPlayerColor(String playerId) throws IllegalArgumentException{
        if (playerIdToPieceColor.get(playerId) == null) {
            throw new IllegalArgumentException("No such player id");
        }
        return playerIdToPieceColor.get(playerId);
    }

    /**
     * Determines if a piece is friendly to the player running the code
     * @param piece Piece to be evaluated
     * @return the result as boolean*/
    public boolean isFriendlyPiece(Piece piece) {
        if (piece == null) return false;
        return piece.getPlayerId().equals(playerID);
    }

    public String getPlayerID() {
        return playerID;
    }

    /**
     * Note to FelixB: When we for example want to move a queen to an opponent the following actions will
     * be sent: STRIKE, DESTRUCTION, MOVEMENT
     *
     * Processes the movement input from the boardview
     * @param  boardView BoardView-Screen that renders game to user
     * @param actionPos Coordinates on the board to be processed
     * */
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

    public void setPlayer(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Determines if the player belongs to the bottom of the board (If the player belongs to y=0 in
     * other words)
     * */
    public boolean playerIsBottom() {
        return playerID.equals(player1Id);
    }
}
