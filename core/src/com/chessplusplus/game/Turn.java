package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Turn {

    public final List<Action> actions;
    public final String playerId;

    public Turn(String playerId, List<Action> actions) {
        this.actions = Collections.unmodifiableList(actions);
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return actions.equals(turn.actions) && playerId.equals(turn.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actions, playerId);
    }

    public static class Action {

        public final Piece piece;           // The piece being affected
        public final ActionType actionType; // The type of action
        public final Position startPos;     // The position of the piece before action is taken
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
        CREATION

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}