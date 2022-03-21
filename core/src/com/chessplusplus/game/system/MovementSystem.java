package com.chessplusplus.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.chessplusplus.game.component.MovementComponent;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.PositionComponent;
import com.chessplusplus.game.component.SizeComponent;
import com.chessplusplus.game.component.movement.CurvingMovementRule;
import com.chessplusplus.game.component.movement.DiagonalMovementRule;
import com.chessplusplus.game.component.movement.HorizontalMovementRule;
import com.chessplusplus.game.component.movement.VerticalMovementRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> pieces;
    private Entity boardEntity;
    private HashMap<Entity, List<Position>> possibleMoves;
    private HashMap<Entity, List<Position>> legalMoves;  //TODO

    @Override
    public void addedToEngine(Engine engine) {
        Family pieces = Family
                .all(PositionComponent.class, MovementComponent.class)
                .get();

        this.pieces = engine.getEntitiesFor(pieces);

        Family board = Family
                .all(SizeComponent.class)
                .get();

        this.boardEntity = engine.getEntitiesFor(board).first();

        updatePossibleMoves();
    }

    private void updatePossibleMoves() {
        for (Entity piece : pieces) {
            //TODO: inefficient component getting
            Position piecePos = piece.getComponent(PositionComponent.class).position;
            SizeComponent sizeComponent = boardEntity.getComponent(SizeComponent.class);
            List<Position> moves = piece.getComponent(MovementComponent.class)
                    .getAllPossibleMoves(piecePos, sizeComponent.width, sizeComponent.height);
            possibleMoves.put(piece, moves);
        }
    }

    @Override
    public void update(float deltaTime) {
        // TODO
        updatePossibleMoves();
    }

}
