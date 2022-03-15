package com.chessplusplus.game.component;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {

    public int x;
    public int y;

    public PositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PositionComponent() {
        x = 0;
        y = 0;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + "]";
    }
}
