package com.chessplusplus.game;

import java.util.List;

/**
 * The Chess Game interface is designed to act as an intermediary between the
 * game itself (constructed and run using the Ashley ECS-system) and the rest
 * of the application. TODO: Update this doc
 *
 * It translates to and from the ECS system to make it easy to interact with the
 * game without having to deal with the increases of the ECS-system.
 */
public interface ChessGame {

    /**
     * Get board.
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
    boolean submitTurn(Turn turn);

    /**
     * Get a list of turns that have happened so far.
     * @return List of previous turns.
     */
    List<Turn> getTurns();

    /**
     * Check if the game has ended (check mate).
     * @return true if the game is over.
     */
    boolean gameIsOver();

}
