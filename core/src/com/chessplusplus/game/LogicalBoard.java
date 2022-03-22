package com.chessplusplus.game;

import com.badlogic.ashley.core.Entity;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.SizeComponent;
import com.chessplusplus.game.entity.Piece;

import java.util.List;


public class LogicalBoard implements Board {

    private final Piece[][] board;
    private final List<Piece> pieces;
    public final Entity boardEntity;

    public LogicalBoard(int size, List<Piece> pieces) {
        boardEntity = new Entity();
        boardEntity.add(new SizeComponent(size, size));

        board = new Piece[size][size];
        this.pieces = pieces;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = null;
            }
        }

        for (Piece piece : pieces) {
            Position position = piece.getPosition();
            board[position.getX()][position.getY()] = piece;
        }
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public int getWidth() {
        return board.length;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    @Override
    public List<Piece> getPieces() {
        return null;
    }

}
