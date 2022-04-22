package com.chessplusplus.game.component;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.ChessBoard;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceFactory;
import com.chessplusplus.game.Turn;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovementComponentTest {

//    TODO: Create movement component with movementRules

//    TODO: Test moves with different pieces

    public static int boardWidth = 8;
    public static int boardHeight = 8;

    /**
     * Visualizes board for easier debugging
     * @param actualMoves returned by the program logic
     * @param expectedMoves written in tests
     * @param piece that can performs move
     * @return string visualizing board and resulting moves
     */
    public String visualizedBoard(List<Position> actualMoves, List<Position> expectedMoves, Position piece){

        StringBuilder output = new StringBuilder(
                "Should all be match M\n" +
                "(M)atch (E)xpected (A)ctual (P)iece\n"
        );

        for (int y = 0; y < boardWidth; y++) {
            for (int x = 0; x < boardHeight; x++) {
                Position position = new Position(x,y);

                boolean actual = actualMoves.contains(position);
                boolean expected = expectedMoves.contains(position);

                String result = "-";
                if(piece.equals(position)) result = "P";
                if(actual) result = "A";
                if(expected) result = "E";

                // correct result at possible move
                if((actual == expected) && expected) result = "M";

                output.append(result).append("  ");
            }
            output.append("\n");
        }
        return output.toString();
    }

    public void testValidMoves(Piece piece, String message, String allExpectedMovesString){
        testValidMoves(piece, Collections.emptyList(), message, allExpectedMovesString);
    }

    public void testValidMoves(Piece piece, List<Piece> otherPieces, String message, String allExpectedMovesString){

        List<Position> expectedMoves = movesToPositions(allExpectedMovesString);

        // generate real result using chessboard logic
//        otherPieces.add(piece);

        List<Piece> pieces = otherPieces;
        Board board = new ChessBoard(pieces, boardWidth, boardHeight);
        List<Turn> turns = piece.getMovementRules().getLegalTurns(piece, board);

        // convert turns to moves
        List<Position> actualMoves = turns.stream()
                .map(turn -> turn.actions.get(0).actionPos)
                .collect(Collectors.toList());

        // create message strings for visualizing board and helper variables
        String visualizedBoard = visualizedBoard(actualMoves, expectedMoves, piece.getPosition());
        String rulesNames = piece.getMovementRules().getStrikePatternsCopy().stream().map(r -> r.getClass().getSimpleName()).collect(Collectors.joining(" "));
        String info = "\nFor rules: " + rulesNames + "\n" + visualizedBoard;
        String assertMessage = message + info;

        // for debugging purposes
        System.out.println(assertMessage);

        // check piece position is not possible move
        Assert.assertFalse(
                "Piece can not move to it's own location" + info,
                actualMoves.stream().anyMatch(m -> m.equals(piece.getPosition()))
        );

        // check move isn't outside board
        List<Position> outsideBounds = actualMoves.stream().filter(m ->
                m.getX() < 0 || m.getY() < 0 ||
                m.getX() >= boardWidth || m.getY() >= boardHeight
        ).collect(Collectors.toList());
        Assert.assertTrue(
                "Moves must be inside bounds\nInvalid move:" + outsideBounds,
                outsideBounds.isEmpty()
        );

        // check returned moves match expected
        // sort because we care about equal content not equal order
        Assert.assertEquals(
                message + info,
                expectedMoves.stream().sorted().collect(Collectors.toList()),
                actualMoves.stream().sorted().collect(Collectors.toList())
        );
    }

    /**
     * Converts string representation to list of moves
     * @param text representing board moves
     * @return all x found to position
     */
    public List<Position> movesToPositions(String text){
        List<Position> positions = new java.util.ArrayList<>(Collections.emptyList());

        String[] rows = text.split("\n");
        for (int y = 0; y < rows.length; y++) {
            String[] piece = rows[y].split(" {2}");
            for (int x = 0; x < piece.length; x++) {
                if(piece[x].equals("x")) positions.add(new Position(x,y));
            }
        }
        return positions;
    }

    @Test
    public void testSimpleKingMove() {
        testValidMoves(
                        PieceFactory.createKing("", new Position(1,1)),
                        "Possible king moves should give expected result",
                        ""+
                       //1  2  3  4  5  6  7  8
                        "x  x  x  -  -  -  -  -  \n" + //1
                        "x  -  x  -  -  -  -  -  \n" + //2
                        "x  x  x  -  -  -  -  -  \n" + //3
                        "-  -  -  -  -  -  -  -  \n" + //4
                        "-  -  -  -  -  -  -  -  \n" + //5
                        "-  -  -  -  -  -  -  -  \n" + //6
                        "-  -  -  -  -  -  -  -  \n" + //7
                        "-  -  -  -  -  -  -  -"       //8
        );
    }

    @Test
    public void testSimpleRookMove() {
        testValidMoves(
                PieceFactory.createRook("", new Position(2,2)),
                "Possible rook moves should give expected result",
                ""+
                        //1  2  3  4  5  6  7  8
                        "-  -  x  -  -  -  -  -  \n" + //1
                        "-  -  x  -  -  -  -  -  \n" + //2
                        "x  x  -  x  x  x  x  x  \n" + //3
                        "-  -  x  -  -  -  -  -  \n" + //4
                        "-  -  x  -  -  -  -  -  \n" + //5
                        "-  -  x  -  -  -  -  -  \n" + //6
                        "-  -  x  -  -  -  -  -  \n" + //7
                        "-  -  x  -  -  -  -  -"       //8
        );
    }

    @Test
    public void testSimpleQueenMove() {
        testValidMoves(
                PieceFactory.createQueen("", new Position(2,2)),
                "Possible Queen moves should give expected result",
                ""+
                        //1  2  3  4  5  6  7  8
                        "x  -  x  -  x  -  -  -  \n" + //1
                        "-  x  x  x  -  -  -  -  \n" + //2
                        "x  x  -  x  x  x  x  x  \n" + //3
                        "-  x  x  x  -  -  -  -  \n" + //4
                        "x  -  x  -  x  -  -  -  \n" + //5
                        "-  -  x  -  -  x  -  -  \n" + //6
                        "-  -  x  -  -  -  x  -  \n" + //7
                        "-  -  x  -  -  -  -  x"       //8
        );
    }

    @Test
    public void testSimpleKnightMove() {
        testValidMoves(
                PieceFactory.createKnight("", new Position(2,2)),
                "Possible Knight moves should give expected result",
                ""+
                        //1  2  3  4  5  6  7  8
                        "-  x  -  x  -  -  -  -  \n" + //1
                        "x  -  -  -  x  -  -  -  \n" + //2
                        "-  -  -  -  -  -  -  -  \n" + //3
                        "x  -  -  -  x  -  -  -  \n" + //4
                        "-  x  -  x  -  -  -  -  \n" + //5
                        "-  -  -  -  -  -  -  -  \n" + //6
                        "-  -  -  -  -  -  -  -  \n" + //7
                        "-  -  -  -  -  -  -  -"       //8
        );
    }

    @Test
    public void testSimpleBishopMove() {
        testValidMoves(
                PieceFactory.createBishop("", new Position(1,1)),
                "Possible Bishop moves should give expected result",
                ""+
                        //1  2  3  4  5  6  7  8
                        "x  -  x  -  -  -  -  -  \n" + //1
                        "-  -  -  -  -  -  -  -  \n" + //2
                        "x  -  x  -  -  -  -  -  \n" + //3
                        "-  -  -  x  -  -  -  -  \n" + //4
                        "-  -  -  -  x  -  -  -  \n" + //5
                        "-  -  -  -  -  x  -  -  \n" + //6
                        "-  -  -  -  -  -  x  -  \n" + //7
                        "-  -  -  -  -  -  -  x"       //8
        );
    }

    // TODO: this fails, can move to it's own location
    @Test
    public void testSimplePawnMove() {
        testValidMoves(
                PieceFactory.createPawn("", new Position(1,1), 1),
                "Possible Bishop moves should give expected result",
                ""+
                        //1  2  3  4  5  6  7  8
                        "-  -  -  -  -  -  -  -  \n" + //1
                        "-  -  -  -  -  -  -  -  \n" + //2
                        "-  x  -  -  -  -  -  -  \n" + //3
                        "-  -  -  -  -  -  -  -  \n" + //4
                        "-  -  -  -  -  -  -  -  \n" + //5
                        "-  -  -  -  -  -  -  -  \n" + //6
                        "-  -  -  -  -  -  -  -  \n" + //7
                        "-  -  -  -  -  -  -  -"       //8
        );
    }



//    DISABLED
//    TODO: Fix castling
//    @Test
    public void testCastlingMove() {
        testValidMoves(
                PieceFactory.createKing("", new Position(4,0)),
                Arrays.asList(
                        PieceFactory.createRook("", new Position(0,0)),
                        PieceFactory.createRook("", new Position(7,0))
                ),
                "should allow king to castle",
                ""+
                       //0  1  2  3  4  5  6  7
                        "-  x  -  x  -  x  x  -  \n" + //0
                        "-  -  -  x  x  x  -  -  \n" + //1
                        "-  -  -  -  -  -  -  -  \n" + //2
                        "-  -  -  -  -  -  -  -  \n" + //3
                        "-  -  -  -  -  -  -  -  \n" + //4
                        "-  -  -  -  -  -  -  -  \n" + //5
                        "-  -  -  -  -  -  -  -  \n" + //6
                        "-  -  -  -  -  -  -  -"       //7
        );
    }

//    DISABLED
//    TODO: Not working atm.
//    @Test
    public void testEnPassantMove() {
        //initialize other pawn that has made one move
        Position startPos = new Position(2,2);
        Position endPos =  new Position(2,1);
        Piece otherPawn = PieceFactory.createPawn("", startPos, -1);
        otherPawn.addAction(new Turn.Action(otherPawn, Turn.ActionType.MOVEMENT,startPos,endPos));

        testValidMoves(
                PieceFactory.createPawn("", new Position(1,1), 1),
                Arrays.asList(otherPawn),
                "should allow pawn to en passant",
                ""+
                        //0  1  2  3  4  5  6  7
                        "-  -  -  -  -  -  -  -  \n" + //0
                        "-  -  -  -  -  -  -  -  \n" + //1
                        "-  x  x  -  -  -  -  -  \n" + //2
                        "-  -  -  -  -  -  -  -  \n" + //3
                        "-  -  -  -  -  -  -  -  \n" + //4
                        "-  -  -  -  -  -  -  -  \n" + //5
                        "-  -  -  -  -  -  -  -  \n" + //6
                        "-  -  -  -  -  -  -  -"       //7
        );
    }


//    TEMPLATE FOR MOVES
//    //0  1  2  3  4  5  6  7
//    "-  -  -  -  -  -  -  -  \n" + //0
//    "-  -  -  -  -  -  -  -  \n" + //1
//    "-  -  -  -  -  -  -  -  \n" + //2
//    "-  -  -  -  -  -  -  -  \n" + //3
//    "-  -  -  -  -  -  -  -  \n" + //4
//    "-  -  -  -  -  -  -  -  \n" + //5
//    "-  -  -  -  -  -  -  -  \n" + //6
//    "-  -  -  -  -  -  -  -"       //7

}







