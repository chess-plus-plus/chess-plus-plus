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
     * Submit a turn to the chess game engine.
     * NB: This may be rejected if it is not legal.
     *
     * @param turn Proposed next turn.
     * @return true if turn is accepted, false if it is rejected
     */
    boolean submitTurn(Turn turn, boolean fromOnline);

    /**
     * Get a list of turns that have happened so far.
     *
     * @return List of previous turns.
     */
    List<Turn> getTurns();

    /**
     * Check if the game has ended (check mate).
     *
     * @return true if the game is over.
     */
    boolean gameIsOver();

}
