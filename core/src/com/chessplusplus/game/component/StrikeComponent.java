package com.chessplusplus.game.component;

import com.chessplusplus.game.component.movement.MovementRule;

import java.util.List;

/**
 * A StrikeComponent is an extension of the MovementComponent because a
 * strike is essentially a just a special move that requires capturing a
 * piece to perform it.
 *
 * It is differentiated from the general movement component to capture
 * chess mechanisms where striking and movements are unrelated (like pawns),
 * and to keep the system easily modifiable.
 */
public class StrikeComponent extends MovementComponent {

    public StrikeComponent(List<MovementRule> strikeRules) {
        super(strikeRules);
    }

}
