package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogicalBoard implements Board {

    private final HashMap<Position, Piece> board = new HashMap<>();
    private final int boardWidth;
    private final int boardHeight;

    public LogicalBoard(List<Piece> pieces, int boardWidth, int boardHeight) {
        for (Piece piece : pieces) {
            board.put(piece.getPosition(), piece);
        }
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public LogicalBoard(List<Piece> pieces) {
        this(pieces, 8, 8);
    }

    @Override
    public int getHeight() {
        return boardHeight;
    }

    @Override
    public int getWidth() {
        return boardWidth;
    }

    @Override
    public Piece getPiece(Position square) {
        return board.get(square);
    }

    @Override
    public Piece getPiece(int x, int y) {
        return getPiece(new Position(x, y));
    }

    @Override
    public List<Piece> getPieces() {
        return new ArrayList<>(board.values());
    }

}
