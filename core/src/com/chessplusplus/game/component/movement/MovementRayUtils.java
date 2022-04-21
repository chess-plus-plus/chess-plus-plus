package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.component.Position;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MovementRayUtils {


    private static boolean inBounds(Position position, int boardWidth, int boardHeight){
        int x = position.getX();
        int y = position.getY();
        return (x > 0 && x < boardWidth) || (y > 0 && y < boardHeight);
    }

    private static Position diagonalPosition(Position piece, int distance, boolean up, boolean right){
        int xDirection = up ? 1 : 0;
        int yDirection = right ? 1 : 0;
        int x = piece.getX() + (distance * xDirection);
        int y = piece.getY() + (distance * yDirection);
        return new Position(x,y);
    }

    public static List<Stream<Position>> getVerticalRays(Position piece, int boardHeight){
        return Arrays.asList(
                IntStream.range(0, piece.getY()).mapToObj(i -> new Position(piece.getX(), i)),
                IntStream.range(piece.getY(), boardHeight).mapToObj(i -> new Position(piece.getX(), i))
        );
    }

    public static List<Stream<Position>> getHorizontalRays(Position piece, int boardWidth){
        return Arrays.asList(
                IntStream.range(0, piece.getX()).mapToObj(i -> new Position(i, piece.getY())),
                IntStream.range(piece.getX(), boardWidth).mapToObj(i -> new Position(i, piece.getY()))
        );
    }

    public static List<Stream<Position>> getDiagonalRays(Position piece, int boardWidth, int boardHeight){
        int size = Math.max(boardWidth, boardHeight);
        IntStream diagonal = IntStream.range(0, size);

        return Arrays.asList(
                diagonal.mapToObj(i -> diagonalPosition(piece, i, true, true)),
                diagonal.mapToObj(i -> diagonalPosition(piece, i, true, false)),
                diagonal.mapToObj(i -> diagonalPosition(piece, i, false, true)),
                diagonal.mapToObj(i -> diagonalPosition(piece, i, false, false))
        );
    }

    public static List<Position> movesFromRay(Stream<Position> ray, Position piece, Board board){

        if(board.getPiece(piece) == null){
            throw new NullPointerException("CHESS++: Trying to calculate moves for a piece that doesn't exist on the board");
        }

        String user = board.getPiece(piece).getPlayerId();
        boolean collided = false;
        List<Position> result = Arrays.asList();

        for(Position position : ray.collect(Collectors.toList())){

            Piece p = board.getPiece(position);

            // no collision or enemy piece
            if(p == null || !p.getPlayerId().equals(user)) result.add(position);

            // collision occurred
            if(p != null) break;
        }

        return result;


//        right.forEachOrdered(System.out::println);
//        System.out.println(piece + " " + boardWidth + " " + boardHeight);

//        return IntStream
//                .range(0, boardWidth * boardHeight)
//                .mapToObj(i -> new Position(i%boardWidth, i/boardWidth))
//
//                .collect(Collectors.toList());
    }

}
