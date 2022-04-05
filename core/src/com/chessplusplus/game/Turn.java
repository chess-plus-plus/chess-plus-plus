package com.chessplusplus.game;

import com.chessplusplus.game.component.Position;

import java.util.Collections;
import java.util.List;

public class Turn {

    public final List<Action> actions;
    public final String playerId;

    public Turn(String playerId, List<Action> actions) {
        this.actions = Collections.unmodifiableList(actions);
        this.playerId = playerId;
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

    }

    public enum ActionType {

        MOVEMENT,
        STRIKE,
        DESTRUCTION,
        CREATION

    }

}