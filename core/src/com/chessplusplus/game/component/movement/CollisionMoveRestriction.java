package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.component.Position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionMoveRestriction implements MoveRestriction{

    static int MAX = 100;

    @Override
    public List<Position> filterMoves(List<Position> possibleMoves, Position piece, Board board) {
        List<List<Position>> horizontal = MovementRayUtils.getHorizontalRays(piece, MAX);
        List<List<Position>> vertical = MovementRayUtils.getVerticalRays(piece, MAX);
        List<List<Position>> diagonal = MovementRayUtils.getDiagonalRays(piece, MAX, MAX);

        diagonal.forEach(System.out::println);

        return Stream.of(horizontal,vertical,diagonal)
                .flatMap(Collection::stream)
                .map(i -> MovementRayUtils.validMovesFromRayCollisionDetection(i, piece, board))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
