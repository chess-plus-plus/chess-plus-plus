package com.chessplusplus.game.component;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {

    public Position position;

    public PositionComponent(int x, int y) {
        position = new Position(x, y);
    }

    public PositionComponent(Position position) {
        this.position = position;
    }

    public PositionComponent() {
        position = new Position(0, 0);
    }

    @Override
    public String toString() {
        return "{x: " + position.getX() + ", y: " + position.getY() + "]";
    }
}
