package com.chessplusplus.game;

import com.chessplusplus.game.entity.Piece;

import java.util.List;

public interface Board {

    public int getHeight();

    public int getWidth();

    public Piece getPiece(int x, int y);

    public List<Piece> getPieces();
}
