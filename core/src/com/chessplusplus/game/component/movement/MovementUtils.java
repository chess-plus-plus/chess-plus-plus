package com.chessplusplus.game.component.movement;

import com.badlogic.gdx.utils.Array;
import com.chessplusplus.game.component.Position;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MovementUtils {

    private static Position diagonalDirection(Position piece, int distance, boolean up, boolean right){
        int xDirection = up ? 1 : 0;
        int yDirection = right ? 1 : 0;
        int x = piece.getX() + (distance * xDirection);
        int y = piece.getY() + (distance * yDirection);
        return new Position(x,y);
    }

    public static List<Position> movesFromRays(Position piece, int boardWidth, int boardHeight){

        int size = Math.max(boardWidth, boardHeight);

        IntStream diagonal = IntStream.range(0, size);

        List<Stream<Position>> rays = Arrays.asList(
                IntStream.range(0, piece.getX()).mapToObj(i -> new Position(i, piece.getY())),
                IntStream.range(0, piece.getY()).mapToObj(i -> new Position(piece.getX(), i)),
                IntStream.range(piece.getX(), boardWidth).mapToObj(i -> new Position(i, piece.getY())),
                IntStream.range(piece.getY(), boardHeight).mapToObj(i -> new Position(piece.getX(), i)),
                diagonal.mapToObj(i -> diagonalDirection(piece, i, true, true)),
                diagonal.mapToObj(i -> diagonalDirection(piece, i, true, false)),
                diagonal.mapToObj(i -> diagonalDirection(piece, i, false, true)),
                diagonal.mapToObj(i -> diagonalDirection(piece, i, false, false))

        );

        rays.stream().map(ray -> {
            int a = 3;
            return 5;
        });

//        right.forEachOrdered(System.out::println);
        System.out.println(piece + " " + boardWidth + " " + boardHeight);

        return IntStream
                .range(0, boardWidth * boardHeight)
                .mapToObj(i -> new Position(i%boardWidth, i/boardWidth))

                .collect(Collectors.toList());
    }

}
