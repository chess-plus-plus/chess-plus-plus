package com.chessplusplus.game;

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

    private final String player1Id;
    private final String player2Id;
    private String currentPlayerId;
    private String playerID; //The player that this code will run on.
    private HashMap<String, PieceColor> playerIdToPieceColor = new HashMap<>();

    private HashMap<Turn, Piece> legalTurnsToPieceMap = new HashMap<>();

    public ChessGameImpl(Board gameBoard, String player1Id, String player2Id) {
        this.gameBoard = gameBoard;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        currentPlayerId = player1Id;
        this.playerID = player1Id;

        calculateAllLegalTurns();

        //Randomly gives players a color and stores in hash map
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        if (randomNum == 0) {
            playerIdToPieceColor.put(player1Id, PieceColor.BLACK);
            playerIdToPieceColor.put(player2Id, PieceColor.WHITE);
        } else {
            playerIdToPieceColor.put(player1Id, PieceColor.WHITE);
            playerIdToPieceColor.put(player2Id, PieceColor.BLACK);
        }
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
    public boolean submitTurn(Turn turn) {
        if (!legalTurnsToPieceMap.containsKey(turn)) {
            return false;
        } else {
            updateGame(turn);
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

        // 2
        for (Turn.Action action : turn.actions) {
            switch (action.actionType) {
                case STRIKE:
                    //TODO: Add XP to striker.
                    break;
                case MOVEMENT:
                    action.piece.moveTo(action.actionPos);
                    break;
                case DESTRUCTION:
                    gameBoard.removePiece(action.piece);
                    break;
                case CREATION:
                    //TODO
                    break;
            }
        }
        gameBoard.updateBoard();
        calculateAllLegalTurns();

        // 3, a bit hacky, but I'm tired and it should work.
        if (currentPlayerId.equals(player1Id)) {
            currentPlayerId = player2Id;
        } else {
            currentPlayerId = player1Id;
        }

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

    public PieceColor getPlayerColor(String playerId) {
        if (playerIdToPieceColor.get(playerId) == null) {
            throw new IllegalArgumentException("No such player id");
        }
        return playerIdToPieceColor.get(playerId);
    }

    public boolean isFriendlyPiece(Piece piece) {
        if (piece == null) return false;
        return piece.getPlayerId().equals(playerID);
    }
}
