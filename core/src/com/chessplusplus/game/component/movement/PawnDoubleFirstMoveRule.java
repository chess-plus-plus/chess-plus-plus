package com.chessplusplus.game.component.movement;

import com.chessplusplus.game.Board;
import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.chessplusplus.game.component.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawns can move 2 squares if they haven't moved yet.
 */
public class PawnDoubleFirstMoveRule implements SpecialMoveRule {

    @Override
    public List<Turn> getLegalTurns(String playerId, Piece pawn, Board gameBoard) {
        ArrayList<Turn> legalTurns = new ArrayList<>();

        // "Move squares" are the squares the pawn wants to move through
        List<Position> moveSquares = getMoveSquares(pawn);

        // Check if the piece is actually a pawn and has yet to move
        if ((pawn.getPieceType() != PieceType.PAWN || pawn.getActions().size() > 0)
                || (moveSquares.size() < 2)) {
            return legalTurns;
        }

        Position firstSquare = moveSquares.get(0);
        Position secondSquare = moveSquares.get(1);

        // Check if both move squares are empty.
        if (gameBoard.squareIsEmpty(firstSquare) && gameBoard.squareIsEmpty(secondSquare)) {
            ArrayList<Turn.Action> actionList = new ArrayList<>();
            actionList.add(new Turn.Action(pawn, Turn.ActionType.MOVEMENT, pawn.getPosition(),
                    secondSquare));

            legalTurns.add(new Turn(playerId, actionList));
        }

        return legalTurns;
    }

    /**
     * Generates a list of 2 positions, the first is the one the pawn will move through,
     * the second is the one the pawn wants to move into.
     *
     * @param pawn      Pawn piece.
     * @return List of 1st and 2nd position.
     */
    private List<Position> getMoveSquares(Piece pawn) {
        List<Position> squares = new ArrayList<>();
        MovementRuleSet pawnMoveSet = pawn.getMovementRules();

        DirectionalMoveRestriction dirRestriction = null;
        for (MoveRestriction restriction : pawnMoveSet.getMoveRestrictionsCopy()) {
            if (restriction.getClass() == DirectionalMoveRestriction.class) {
                dirRestriction = (DirectionalMoveRestriction) restriction;
            }
        }

        if (dirRestriction == null) {
            return squares;
        }

        int xVal = pawn.getPosition().getX();
        int yVal = pawn.getPosition().getY();
        int dirDelta = dirRestriction.dirFilterY;
        squares.add(new Position(xVal, yVal+dirDelta));
        squares.add(new Position(xVal, yVal+dirDelta+dirDelta));

        return squares;
    }

}
