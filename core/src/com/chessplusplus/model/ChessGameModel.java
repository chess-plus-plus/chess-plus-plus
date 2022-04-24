package com.chessplusplus.model;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.level.LevelSystem;
import com.chessplusplus.model.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A implementation of the ChessGame interface.
 * Contains a game board, a list of turns, and player IDs.
 */
public class ChessGameModel implements ChessGame {

    private final Board gameBoard;
    private final List<ChessTurn> gameTurnHistory = new ArrayList<>();
    private final LevelSystem levelSystem;

    private final int MOVE_XP;
    private final int STRIKE_XP;

    private final String player1Id;
    private final String player2Id;
    private String currentPlayerId;

    private final HashMap<ChessTurn, Piece> legalTurnsToPieceMap = new HashMap<>();

    public ChessGameModel(Board gameBoard, String player1Id, String player2Id,
                          int MOVE_XP, int STRIKE_XP, LevelSystem levelSystem) {
        this.gameBoard = gameBoard;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.MOVE_XP = MOVE_XP;
        this.STRIKE_XP = STRIKE_XP;
        this.levelSystem = levelSystem;
        currentPlayerId = player1Id;

        calculateAllLegalTurns();
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
    public void submitTurn(ChessTurn turn, boolean fromOnline) {
        updateGame(turn);
    }

    @Override
    public boolean turnIsLegal(ChessTurn turn) {
        return legalTurnsToPieceMap.containsKey(turn);
    }

    /**
     * Updates game state:
     * 1: Add turn to turn history.
     * 2: Update board.
     * 3: Update currentPlayerId;
     *
     * @param turn A valid turn.
     */
    private void updateGame(ChessTurn turn) {
        // 1
        gameTurnHistory.add(turn);
        System.out.println("New turn");
        // 2
        for (ChessTurn.Action action : turn.actions) {
            Piece startPiece = gameBoard.getPiece(action.startPos);
            Piece actionPiece = gameBoard.getPiece(action.actionPos);
            switch (action.actionType) {
                case STRIKE:
                    startPiece.giveXp(STRIKE_XP, levelSystem, gameBoard);
                    gameBoard.removePiece(actionPiece);
                    break;
                case MOVEMENT:
                    startPiece.moveTo(action.actionPos);
                    startPiece.giveXp(MOVE_XP, levelSystem, gameBoard);
                    startPiece.addAction(action);
                    break;
                case DESTRUCTION:
                    gameBoard.removePiece(actionPiece);
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
            List<ChessTurn> turns = piece.getLegalTurns(gameBoard);
            for (ChessTurn turn : turns) {
                legalTurnsToPieceMap.put(turn, piece);
            }
        }
    }

    @Override
    public List<ChessTurn> getTurns() {
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

}
