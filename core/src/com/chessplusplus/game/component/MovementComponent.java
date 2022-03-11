package com.chessplusplus.game.component;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {

    public int deltaX;
    public int deltaY;

    public MovementComponent(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

}
