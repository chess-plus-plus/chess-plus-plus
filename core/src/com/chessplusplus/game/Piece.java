package com.chessplusplus.game;

import com.badlogic.gdx.graphics.Texture;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.movement.MovementRuleSet;
import com.google.gson.annotations.Expose;
import com.chessplusplus.game.system.LevelEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A chess piece.
 */
public class Piece {

    @Expose
    private String playerId;
    @Expose
    private PieceType pieceType;
    @Expose
    private Position position;
    private MovementRuleSet movement;
    private PieceColor color;
    private Texture texture;
    private String textureFileName;

    private int level = 0;
    private int xp = 0;
    private int nextLevelXpThreshold;
    private int prevNextLevelXpThreshold = 0; //The xp needed to be in the current level
    private final List<Turn.Action> actions = new ArrayList<>(); // All actions the piece have made

    public Piece(String playerId, PieceType pieceType, Position position, MovementRuleSet movement,
                 int nextLevelXpThreshold) {
        this.playerId = playerId;
        this.pieceType = pieceType;
        this.position = position;
        this.movement = movement;
        this.nextLevelXpThreshold = nextLevelXpThreshold;

        //Determines filename to texture image to use for the piece.
        String typePath = "pawn";
        switch (pieceType) {
            case KING:
                typePath = "king.png";
                break;
            case PAWN:
                typePath = "pawn.png";
                break;
            case ROOK:
                typePath = "rook.png";
                break;
            case QUEEN:
                typePath = "queen.png";
                break;
            case BISHOP:
                typePath = "bishop.png";
                break;
            case KNIGHT:
                typePath = "knight.png";
                break;
        }
        textureFileName = typePath;
    }

    public Piece(String playerId, PieceType pieceType, Position position, MovementRuleSet movement) {
        this(playerId, pieceType, position, movement, Integer.MAX_VALUE);
    }

    /**
     * @return playerID of the piece's owner.
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Change the playerID of the piece.
     *
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
     *
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
     *
     * @param newPosition New position of the piece.
     */
    public void moveTo(Position newPosition) {
        position = newPosition;
    }

    /**
     * Get all legal turns the piece can take.
     *
     * @param gameBoard Game board.
     * @return Set of all legal turns.
     */
    public List<Turn> getLegalTurns(Board gameBoard) {
        return movement.getLegalTurns(this, gameBoard);
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

    /**
     * Give the piece a certain amount of XP.
     * This may cause the piece to level up.
     *
     * @param xp          Amount of xp to give the piece.
     * @param levelEngine Used to upgrade the piece.
     */
    public void giveXp(int xp, LevelEngine levelEngine) {
        this.xp += xp;
        if (this.xp >= nextLevelXpThreshold) {
            level++;
            prevNextLevelXpThreshold = nextLevelXpThreshold;
            nextLevelXpThreshold = Integer.MAX_VALUE;
            levelEngine.levelUp(this);
        }
    }

    /**
     * Get the XP threshold for next level.
     *
     * @return Next XP threshold.
     */
    public int getNextLevelXpThreshold() {
        return nextLevelXpThreshold;
    }

    /**
     * Sets the XP threshold for the piece's next level.
     * Once this threshold is exceeded the piece levels up.
     *
     * @param nextLevelXpThreshold Next XP threshold.
     */
    public void setNextLevelXpThreshold(int nextLevelXpThreshold) {
        this.nextLevelXpThreshold = nextLevelXpThreshold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && position.equals(piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, position);
    }

    public List<Turn.Action> getActions() {
        return actions;
    }

    public void addAction(Turn.Action action) {
        actions.add(action);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public int getPrevNextLevelXpThreshold() { return this.prevNextLevelXpThreshold; }

    public boolean equals(Piece piece) {
        return this.position.equals(piece.getPosition());
    }

    /**
     * Sets texture of Piece. Only call this once to setup texture based on what color the player and piece belong to
     *
     * @param filePath filepath to relevant directory. Currently either "pieces/black/" or "pieces/white/"
     */
    public void setTexture(String filePath) {
        //Safety dispose in case redundant calls to method
        if (texture != null) {
            texture.dispose();
        }
        filePath += textureFileName;
        texture = new Texture(filePath);
    }

    @Override
    public String toString() {
        return String.format("Piece: %s\nPosition: %s", this.pieceType, this.position);
    }

}
