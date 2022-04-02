package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.MovementRuleSet;

import java.util.List;

public class Piece {

    private Position position;
    private MovementRuleSet movementRuleSet;

    int xp = 0;

    public Piece(Position position, MovementRuleSet movementRuleSet) {
        this.position = position;
        this.movementRuleSet = movementRuleSet;
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getLegalMoves(int boardWidth, int boardHeight) {
        return movementRuleSet.getLegalMoves(position, boardWidth, boardHeight);
    }

}
