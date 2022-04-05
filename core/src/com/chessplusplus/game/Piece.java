package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.MovementRuleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A chess piece.
 */
public class Piece {

    private String playerId;
    private PieceType pieceType;
    private Position position;
    private MovementRuleSet movement;

    private int xp = 0;
    private final List<Turn.Action> actions = new ArrayList<>(); // All actions the piece have made

    public Piece(String playerId, PieceType pieceType, Position position, MovementRuleSet movement) {
        this.playerId = playerId;
        this.pieceType = pieceType;
        this.position = position;
        this.movement = movement;
    }

    /**
     * @return playerID of the piece's owner.
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Change the playerID of the piece.
     * @param playerId new playerID.
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return Piece type of the piece.
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Change the piece type of the piece.
     * @param pieceType new piece type.
     */
    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * @return Position of piece.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Move the piece to a new position.
     * @param newPosition New position of the piece.
     */
    public void moveTo(Position newPosition) {
        position = newPosition;
    }

    /**
     * Generates a set of turns the piece can initiate according to its rules.
     *
     * @param boardWidth  Width of the game board.
     * @param boardHeight Height of the game board.
     * @return Set of all possible moves.
     */
    public List<Turn> getLegalTurns(int boardWidth, int boardHeight) {
        return null;
    }

    /**
     * Get the movement rule-set.
     *
     * @return Movement rule-set
     */
    public MovementRuleSet getMovementRules() {
        return movement;
    }

    public void setMovement(MovementRuleSet movement) {
        this.movement = movement;
    }

    public int getXp() {
        return xp;
    }

    public void giveXp(int xp) {
        this.xp += xp;
    }

    public List<Turn.Action> getActions() {
        return actions;
    }

    public void addAction(Turn.Action action) {
        actions.add(action);
    }

}
