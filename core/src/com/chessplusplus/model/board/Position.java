package com.chessplusplus.model.board;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Position implements Comparable<Position>{
    @Expose
    private final int x;
    @Expose
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Short-hand utility method that can be imported statically to save time.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return new Position object
     */
    public static Position pos(int x, int y) {
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position adjacentLeftPosition() {
        return new Position(x - 1, y);
    }

    public Position adjacentRightPosition() {
        return new Position(x + 1, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Position position) {
        return this.hashCode() - position.hashCode();
    }
}
