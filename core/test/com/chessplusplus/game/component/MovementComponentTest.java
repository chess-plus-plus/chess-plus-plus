package com.chessplusplus.game.component;

import static com.chessplusplus.game.component.movement.HorizontalMovementRule.oneSquareHorizontalMovement;
import static com.chessplusplus.game.component.movement.VerticalMovementRule.oneSquareVerticalMovement;
import static com.chessplusplus.game.component.movement.VerticalMovementRule.unlimitedVerticalMovement;

import com.chessplusplus.game.component.movement.MovementRule;
import com.chessplusplus.game.component.movement.SimpleMovementRule;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    public void testValidMoves(List<MovementRule> rules, Position piecePosition, List<Position> expectedMoves, String message){

        MovementComponent movementComponent = new MovementComponent(rules);

        List<Position> actualMoves = movementComponent.getAllPossibleMoves(
                piecePosition, boardWidth, boardHeight
        );

        String visualizedBoard = visualizedBoard(actualMoves, expectedMoves, piecePosition);
        String rulesNames = rules.stream().map(r -> r.getClass().getSimpleName()).collect(Collectors.joining(" "));
        String assertMessage = message + "\nFor rules: " + rulesNames + "\n" + visualizedBoard;

        Assert.assertEquals(
                assertMessage,
                expectedMoves,
                actualMoves
        );
    }

    @Test
    public void tesKingMoves() {

        List<MovementRule> rules = Arrays.asList(
                oneSquareHorizontalMovement(),
                oneSquareVerticalMovement()
        );

        Position piecePosition = new Position(2,2);

        List<Position> expectedMoves = Arrays.asList(
                new Position(1,2),
                new Position(2,1),
                new Position(3,2),
                new Position(2,3)
        );

        String message = "Possible king moves should give expected result";

        testValidMoves(rules, piecePosition, expectedMoves, message);
    }


}







