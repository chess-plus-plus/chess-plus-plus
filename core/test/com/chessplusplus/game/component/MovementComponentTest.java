package com.chessplusplus.game.component;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.BoardFactory;
import com.chessplusplus.game.ChessBoard;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.Turn;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovementComponentTest {

//    TODO: Create movement component with movementRules

//    TODO: Test moves with different pieces

    public static int boardWidth = 10;
    public static int boardHeight = 10;

    public String visualizedBoard(List<Position> actualMoves, List<Position> expectedMoves, Position piece){

        StringBuilder output = new StringBuilder(
                "Should all be match M\n" +
                "(M)atch (E)xpected (A)ctual (P)iece\n"
        );

        for (int y = 1; y < boardWidth; y++) {
            for (int x = 1; x < boardHeight; x++) {
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


    public void testValidMoves(Piece piece, List<Position> expectedMoves, String message){

        Board board = new ChessBoard(Arrays.asList(piece), boardWidth, boardHeight);
        List<Turn> turns = piece.getMovementRules().getLegalTurns(piece, board);


        List<Position> actualMoves = turns.stream().map(turn -> turn.actions.get(0).actionPos).collect(Collectors.toList());


        String visualizedBoard = visualizedBoard(actualMoves, expectedMoves, piece.getPosition());
        String rulesNames = piece.getMovementRules().getStrikePatternsCopy().stream().map(r -> r.getClass().getSimpleName()).collect(Collectors.joining(" "));
        String assertMessage = message + "\nFor rules: " + rulesNames + "\n" + visualizedBoard;

        Assert.assertEquals(
                assertMessage,
                expectedMoves,
                actualMoves
        );
    }

    public String moveSpaceToPositions(String text){
        rows = text.split("\n");
    }

    @Test
    public void tesKingMoves() {

        Position piecePosition = new Position(2,2);

        Piece piece = BoardFactory.createKing("123", piecePosition);

        String expectedMoves =
              // 1  2  3  4  5  6  7  8  9
                "x  x  x  -  -  -  -  -  -  \n" + //1
                "x  -  x  -  -  -  -  -  -  \n" + //2
                "x  x  x  -  -  -  -  -  -  \n" + //3
                "-  -  -  -  -  -  -  -  -  \n" + //4
                "-  -  -  -  -  -  -  -  -  \n" + //5
                "-  -  -  -  -  -  -  -  -  \n" + //6
                "-  -  -  -  -  -  -  -  -  \n" + //7
                "-  -  -  -  -  -  -  -  -  \n" + //8
                "-  -  -  -  -  -  -  -  -    ";  //9

//        List<Position> expectedMoves = Arrays.asList(
//                new Position(1,2),
//                new Position(2,1),
//                new Position(3,2),
//                new Position(2,3)
//        );

        String message = "Possible king moves should give expected result";

        testValidMoves(piece, expectedMoves, message);
    }


}







