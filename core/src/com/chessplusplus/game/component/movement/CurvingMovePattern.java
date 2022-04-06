package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * CurvingMovePattern is used to represent complex moves where the
 * piece moves in an L-like curve according to a given ratio.
 *
 * This movement is represented as a ratio of squares the piece can move
 * along in a turn. For example, the knight has a curving movement ratio of
 * 1:2, e.g. for every 1 square it moves in 1 axis it has to move 2 squares
 * along the other axis.
 */
public class CurvingMovePattern implements MovePattern {

    public int ratioNum1;
    public int ratioNum2;

    public CurvingMovePattern(int ratioNum1, int ratioNum2) {
        this.ratioNum1 = ratioNum1;
        this.ratioNum2 = ratioNum2;
    }

    /**
     * Creates a movement pattern that gives the piece a curving movement
     * with a ratio of 1:2, following an "L"-shape.
     *
     * In normal chess this is used by the Knight.
     * @return CurvingMovePattern with ratio 1:2.
     */
    public static CurvingMovePattern standardKnightMovement() {
        return new CurvingMovePattern(1, 2);
    }

    @Override
    public List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight) {
        List<Position> possibleMoves = new ArrayList<>();

        int pieceX = piecePosition.getX();
        int pieceY = piecePosition.getY();

        possibleMoves.add(new Position(pieceX + ratioNum1, pieceY + ratioNum2));
        possibleMoves.add(new Position(pieceX + ratioNum2, pieceY + ratioNum1));
        possibleMoves.add(new Position(pieceX + ratioNum2, pieceY - ratioNum1));
        possibleMoves.add(new Position(pieceX + ratioNum1, pieceY - ratioNum2));
        possibleMoves.add(new Position(pieceX - ratioNum1, pieceY - ratioNum2));
        possibleMoves.add(new Position(pieceX - ratioNum2, pieceY - ratioNum1));
        possibleMoves.add(new Position(pieceX - ratioNum2, pieceY + ratioNum1));
        possibleMoves.add(new Position(pieceX - ratioNum1, pieceY + ratioNum2));

        //TODO: Needs to be tested
        List<Position> legalPossibleMoves = new ArrayList<>();
        for (Position position : possibleMoves) {
            if (position.getX() >= 0 && position.getX() < boardWidth
                    && position.getY() >= 0 && position.getY() < boardHeight) {
                legalPossibleMoves.add(position);
            }
        }

        return legalPossibleMoves;
    }

}
