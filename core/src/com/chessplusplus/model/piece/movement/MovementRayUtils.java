package com.chessplusplus.model.piece.movement;

import com.chessplusplus.model.board.Board;
import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.board.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * TODO: This should all be refactored into a rayBatch class
 * TODO: getRays method could then be moved into a factory
 *
 * Rays for collision detection so pieces can't move trough each other
 */
public class MovementRayUtils {

    private static boolean inBounds(Position position, int boardWidth, int boardHeight){
        int x = position.getX();
        int y = position.getY();
        return (x > 0 && x < boardWidth) || (y > 0 && y < boardHeight);
    }

    //Given diagonal (up/right boolean) which position is n distance from piece
    private static Position diagonalPosition(Position piece, int distance, boolean up, boolean right){
        int xDirection = up ? 1 : -1;
        int yDirection = right ? 1 : -1;
        int x = piece.getX() + (distance * xDirection);
        int y = piece.getY() + (distance * yDirection);
        return new Position(x,y);
    }

    //Helper function for static get rays methods
    @SafeVarargs
    private static List<List<Position>> raysToList(Stream<Position>... rays){
        return Arrays.stream(rays)
                .map(i -> i.collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public static List<List<Position>> getVerticalRays(Position piece, int boardHeight){
        Stream<Position> up = IntStream
                .range(0, piece.getY())
                .mapToObj(i -> new Position(piece.getX(), i))
                .sorted(Collections.reverseOrder());
        Stream<Position> down = IntStream
                .range(piece.getY()+1, boardHeight)
                .mapToObj(i -> new Position(piece.getX(), i));

        return raysToList(up,down);
    }

    public static List<List<Position>> getHorizontalRays(Position piece, int boardWidth){
        Stream<Position> left = IntStream
                .range(0, piece.getX())
                .mapToObj(i -> new Position(i, piece.getY()))
                .sorted(Collections.reverseOrder());
        Stream<Position> right = IntStream
                .range(piece.getX()+1, boardWidth)
                .mapToObj(i -> new Position(i, piece.getY()));

        return raysToList(left,right);
    }

    public static List<List<Position>> getDiagonalRays(Position piece, int boardWidth, int boardHeight){
        int size = Math.max(boardWidth, boardHeight);
        return raysToList(
                IntStream.range(1, size).mapToObj(i -> diagonalPosition(piece, i, true, true)),
                IntStream.range(1, size).mapToObj(i -> diagonalPosition(piece, i, true, false)),
                IntStream.range(1, size).mapToObj(i -> diagonalPosition(piece, i, false, true)),
                IntStream.range(1, size).mapToObj(i -> diagonalPosition(piece, i, false, false))
        );
    }

    /**
     * Takes a single ray and calculates first piece collision returning only valid moves
     * Takes into account different case for enemy and ally pieces
     * @param ray
     * @param piece that is making a move (need this to determine ally from enemy pieces)
     * @param board of other pieces
     * @return valid moves given collision if any
     */
    public static List<Position> validMovesFromRayCollisionDetection(List<Position> ray, Position piece, Board board){

        if(board.getPiece(piece) == null) throw new NullPointerException("CHESS++: Trying to calculate moves for a piece that doesn't exist on the board");

        String user = board.getPiece(piece).getPlayerId();
        List<Position> result = new ArrayList<>();

        for(Position position : ray){

            Piece p = board.getPiece(position);

            // no collision or enemy piece
            if(p == null || !p.getPlayerId().equals(user)) result.add(position);

            // collision occurred
            if(p != null) break;
        }

        return result;
    }

    // Utility function created for RookChainAttackMoveRule, it should work based on my testing.
    public static List<Position> adjacentEnemyRayCalc(List<Position> ray, String playerId, Board board) {
        List<Position> positions = new ArrayList<>();

        boolean enemyPieceEncountered = false;
        for (Position position : ray) {
            Piece p = board.getPiece(position);

            if (p != null && !p.getPlayerId().equals(playerId)) {
                enemyPieceEncountered = true;
                positions.add(position);
            } else if (enemyPieceEncountered) {
                break;
            }
        }

        return positions;
    }

    /**
     * Creates "wrapping rays", that wrap around the board.
     *
     * @param startPos Position of piece, will not be included in the ray.
     * @param xDelta   -1 for "west", 0 for neutral, 1 for "east".
     * @param yDelta   -1 for "south", 0 for neutral, 1 for "north".
     * @param board    Game board.
     * @return Ray of positions.
     */
    public static List<Position> wrappingRays(Position startPos, int xDelta, int yDelta, Board board) {
        List<Position> positions = new ArrayList<>();

        int northEdge = board.getHeight() - 1;
        int eastEdge = board.getWidth() - 1;
        int southEdge = 0;
        int westEdge = 0;

        Position tmp = startPos;
        do {
            tmp = new Position(tmp.getX() + xDelta, tmp.getY() + yDelta);
            if (tmp.getX() < westEdge) {
                tmp = new Position(eastEdge, tmp.getY());
            } else if (tmp.getX() > eastEdge) {
                tmp = new Position(westEdge, tmp.getY());
            }

            if (tmp.getY() < southEdge) {
                tmp = new Position(tmp.getX(), northEdge);
            } else if (tmp.getY() > northEdge) {
                tmp = new Position(tmp.getX(), southEdge);
            }

            positions.add(tmp);
        } while (!tmp.equals(startPos));

        positions.remove(startPos);

        return positions;
    }

}
