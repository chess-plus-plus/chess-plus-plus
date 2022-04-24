package com.chessplusplus.game;

import java.util.List;

/**
 * The Chess Game interface is used to interact with the game at a high level
 * of abstraction. It gives access to the board, and can be used to retrieve and
 * submit turns.
 */
public interface ChessGame {

    /**
     * Get board.
     *
     * @return Board object.
     */
    Board getBoard();

    /**
     * @return Get player id of the player who has the next turn.
     */
    String getCurrentPlayer();

    /**
     * Checks if a turn is legal according to the current game state.
     *
     * @param turn Proposed turn.
     * @return true if the turn is legal, false otherwise.
     */
    boolean turnIsLegal(ChessTurn turn);

    /**
     * Submit a turn to the chess game engine.
     *
     * @param turn Proposed next turn.
     */
    void submitTurn(ChessTurn turn, boolean fromOnline);

    /**
     * Get a list of turns that have happened so far.
     *
     * @return List of previous turns.
     */
    List<ChessTurn> getTurns();

    /**
     * Check if the game has ended (check mate).
     *
     * @return true if the game is over.
     */
    boolean gameIsOver();



}
