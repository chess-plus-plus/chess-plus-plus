package com.chessplusplus.game;

import com.badlogic.ashley.core.Entity;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.PositionComponent;
import com.chessplusplus.game.component.SizeComponent;

import java.util.List;


public class LogicalBoard implements Board {

    private final Entity[][] board;
    private final List<Entity> pieces;
    public final Entity boardEntity;

    public LogicalBoard(int size, List<Entity> pieces) {
        boardEntity = new Entity();
        boardEntity.add(new SizeComponent(size, size));

        board = new Entity[size][size];
        this.pieces = pieces;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = null;
            }
        }

        for (Entity piece : pieces) {
            Position position = piece.getComponent(PositionComponent.class).position;
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
    public Entity getPiece(int x, int y) {
        return board[x][y];
    }

    @Override
    public List<Entity> getPieces() {
        return null;
    }

}
