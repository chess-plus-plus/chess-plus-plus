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
import java.util.stream.IntStream;

public class MovementComponentTest {

//    TODO: Create movement component with movementRules

//    TODO: Test moves with different pieces

    public static int boardWidth = 10;
    public static int boardHeight = 10;

    @Test
    public void tesKingMoves() {

        List<MovementRule> rules = Arrays.asList(
                unlimitedVerticalMovement()
//                ,
//                oneSquareVerticalMovement()
        );

        MovementComponent movementComponent = new MovementComponent(rules);
        Position piecePosition = new Position(2,2);


        List<Position> possibleMoves = movementComponent.getAllPossibleMoves(
                piecePosition, boardWidth, boardHeight
        );

        List<Position> solution = Arrays.asList(
                new Position(0,1),
                new Position(1,0),
                new Position(2,1),
                new Position(1,2)
        );


        IntStream.range(0,boardWidth).forEachOrdered(y -> {
            IntStream.range(0,boardHeight).forEachOrdered(x -> {
                Position position = new Position(x,y);

                boolean actual = possibleMoves.contains(position);
                boolean expected = solution.contains(position);

                boolean match = actual == expected;
                String result = "- ";
                if(match && expected) result = "O ";
                if(!match) result = "x ";

                System.out.print(result);
            });
            System.out.println();
        });

        System.out.println(possibleMoves);


        Assert.assertEquals(
                "Possible king moves should give expected result",
                solution,
                possibleMoves
        );

    }
}







