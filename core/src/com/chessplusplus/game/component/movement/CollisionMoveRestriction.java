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
        List<Stream<Position>> horizontal = MovementRayUtils.getHorizontalRays(piece, MAX);
        List<Stream<Position>> vertical = MovementRayUtils.getVerticalRays(piece, MAX);
        List<Stream<Position>> diagonal = MovementRayUtils.getDiagonalRays(piece, MAX, MAX);

        return Stream.of(horizontal,vertical,diagonal)
                .flatMap(Collection::stream)
                .map(i -> MovementRayUtils.movesFromRay(i, piece, board))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
