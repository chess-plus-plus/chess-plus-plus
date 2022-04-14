package com.chessplusplus.game;

public enum PieceColor {
    BLACK,
    WHITE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
