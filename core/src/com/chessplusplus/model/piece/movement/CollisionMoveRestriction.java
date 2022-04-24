package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.board.Position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionMoveRestriction implements MoveRestriction{



    @Override
    public List<Position> filterMoves(List<Position> possibleMoves, Position piece, Board board) {

        // max distance takes into account looping
        int MAX = Math.max(board.getHeight(), board.getWidth()) * 2;

        List<List<Position>> horizontal = MovementRayUtils.getHorizontalRays(piece, MAX);
        List<List<Position>> vertical = MovementRayUtils.getVerticalRays(piece, MAX);
        List<List<Position>> diagonal = MovementRayUtils.getDiagonalRays(piece, MAX, MAX);

        List<Position> result = Stream.of(horizontal, vertical, diagonal)
                .flatMap(Collection::stream)
                .map(i -> MovementRayUtils.validMovesFromRayCollisionDetection(i, piece, board))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return result;
    }
}
