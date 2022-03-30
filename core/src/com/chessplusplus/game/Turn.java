package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.Collections;
import java.util.List;

public class Turn {

    public final List<Action> actions;

    public Turn(List<Action> actions) {
        this.actions = Collections.unmodifiableList(actions);
    }

    public static class Action {

        public final Piece piece;           // The piece being affected
        public final ActionType actionType; // The type of action
        public final Position position;     // The position of the piece at the end of the turn

        public Action(Piece piece, ActionType actionType, Position position) {
            this.piece = piece;
            this.actionType = actionType;
            this.position = position;
        }

    }

    public enum ActionType {

        MOVEMENT,
        STRIKE,
        DESTRUCTION,
        CREATION

    }

}