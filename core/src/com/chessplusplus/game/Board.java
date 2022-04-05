package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.List;

public interface Board {

    int getHeight();

    int getWidth();

    Piece getPiece(Position position);

    boolean squareIsEmpty(Position position);

    boolean squareIsUnderAttack(Position position, String playerId);

    List<Piece> getAllPieces();

    /**
     * Updates the board after the board has been edited.
     */
    void updateBoard();

    void removePiece(Piece piece);

}
