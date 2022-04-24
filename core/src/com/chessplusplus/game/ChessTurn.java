package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChessTurn {
    @Expose
    public final List<Action> actions;
    @Expose
    public final String playerId;

    public ChessTurn(String playerId, List<Action> actions) {
        this.actions = Collections.unmodifiableList(actions);
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessTurn turn = (ChessTurn) o;
        return actions.equals(turn.actions) && playerId.equals(turn.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actions, playerId);
    }

    public static class Action {

        @Expose
        public final Piece piece;// The piece being affected
        @Expose
        public final ActionType actionType; // The type of action
        @Expose
        public final Position startPos;     // The position of the piece before action is taken
        @Expose
        public final Position actionPos;    // The position where the piece strikes/moves to

        public Action(Piece piece, ActionType actionType, Position startPos, Position actionPos) {
            this.piece = piece;
            this.actionType = actionType;
            this.startPos = startPos;
            this.actionPos = actionPos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Action action = (Action) o;
            return piece.equals(action.piece) && actionType == action.actionType && startPos.equals(action.startPos) && actionPos.equals(action.actionPos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(piece, actionType, startPos, actionPos);
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public enum ActionType {

        MOVEMENT,
        STRIKE,
        DESTRUCTION,
        CREATION,
        UPGRADE

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}