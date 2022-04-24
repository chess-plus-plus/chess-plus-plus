package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.List;

/**
 * The board interface represents a stateless board.
 * It has dimensions, squares (which may or may not contain pieces),
 * and a few utility methods.
 */
public interface Board {

    /**
     * Get the height of the board.
     *
     * @return Board height.
     */
    int getHeight();

    /**
     * Get the width of the board.
     *
     * @return Board width.
     */
    int getWidth();

    /**
     * Get a piece in a square.
     *
     * @param position Position on the board.
     * @return Piece in given position, or null.
     */
    Piece getPiece(Position position);

    /**
     * Utility method to check if a square is empty.
     *
     * @param position Position on the board.
     * @return True if square is empty, or false otherwise.
     */
    boolean squareIsEmpty(Position position);

    /**
     * Utility method to check if a square is under attack/threatened by a specific player.
     *
     * @param position Position on the board.
     * @param playerId ID of player.
     * @return True if the square is threatened by the player.
     */
    boolean squareIsUnderAttack(Position position, String playerId);

    /**
     * Get a list of all the pieces present on the board.
     *
     * @return List of all pieces.
     */
    List<Piece> getAllPieces();

    /**
     * Updates the board after the board has been edited.
     */
    void updateBoard();

    /**
     * Remove a piece from the board.
     *
     * @param piece Piece to remove from the board.
     */
    void removePiece(Piece piece);

    /**
     * Adds a piece to the board.
     *
     * @param piece    Piece to add to the board.
     * @param position Position to add the piece.
     */
    void addPiece(Piece piece, Position position);

}
