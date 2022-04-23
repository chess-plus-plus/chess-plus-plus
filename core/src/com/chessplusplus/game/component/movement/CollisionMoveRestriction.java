package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.component.Position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionMoveRestriction implements MoveRestriction{



    @Override
    public List<Position> filterMoves(List<Position> possibleMoves, Position piece, Board board) {

        // max distance takes into account looping
        int MAX = Math.max(board.getHeight(), board.getWidth()) * 2;

        System.out.println("-------------------------\npossible moves");
        System.out.println(possibleMoves);

        List<List<Position>> horizontal = MovementRayUtils.getHorizontalRays(piece, MAX);
        List<List<Position>> vertical = MovementRayUtils.getVerticalRays(piece, MAX);
        List<List<Position>> diagonal = MovementRayUtils.getDiagonalRays(piece, MAX, MAX);

        System.out.println("Horizontal move restrictions for " + this.getClass().getName());
        horizontal.forEach(System.out::println);

        List<Position> result = Stream.of(horizontal, vertical, diagonal)
                .flatMap(Collection::stream)
                .map(i -> MovementRayUtils.validMovesFromRayCollisionDetection(i, piece, board))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("Results for collision " + this.getClass().getName());
        System.out.println(result);

        return result;
    }
}
