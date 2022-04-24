package com.chessplusplus.model.piece;

public enum PieceColor {
    BLACK,
    WHITE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
