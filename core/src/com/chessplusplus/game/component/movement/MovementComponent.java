package com.chessplusplus.game.component.movement;

import com.badlogic.ashley.core.Component;
import com.chessplusplus.game.component.Position;

import java.util.List;

/**
 * The MovementComponent interface is used for components that define the
 * possible and legal ways a piece can move on the board.
 */
public interface MovementComponent extends Component {

    /**
     * Utility method all movement components must implement that generates a list of
     * all moves that could be made as a result of this component.
     *
     * @param piecePosition The position of the piece.
     * @param boardWidth    Width of game board.
     * @param boardHeight   Height of game board.
     * @return All possible moves this movement rule implies.
     */
    List<Position> getPossibleMoves(Position piecePosition, int boardWidth, int boardHeight);

}
