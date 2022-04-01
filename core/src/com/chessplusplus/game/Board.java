package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.List;

public interface Board {

    int getHeight();

    int getWidth();

    Piece getPiece(Position position);

    Piece getPiece(int x, int y);

    List<Piece> getPieces();
}
