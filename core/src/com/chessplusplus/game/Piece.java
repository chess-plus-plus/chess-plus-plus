package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.MovementRuleSet;

import java.util.List;

//TODO: Needs cleaning up and more comments
public class Piece {

    private int teamId;
    private PieceType pieceType;
    private Position position;
    private MovementRuleSet movementRuleSet;

    private int xp = 0;
    private int moves = 0; // How many times the piece has moved.

    public Piece(Position position, MovementRuleSet movementRuleSet) {
        this.position = position;
        this.movementRuleSet = movementRuleSet;
    }

    public int getMoves() {
        return moves;
    }

    public int getTeamId() {
        return teamId;
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getLegalMoves(int boardWidth, int boardHeight) {
        return movementRuleSet.getLegalMoves(position, boardWidth, boardHeight);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getXp() {
        return xp;
    }
}
