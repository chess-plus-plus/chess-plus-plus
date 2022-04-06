package com.chessplusplus.game;

import com.badlogic.ashley.core.Entity;

import java.util.List;

public interface Board {

    public int getHeight();

    public int getWidth();

    public Entity getPiece(int x, int y);

    public List<Entity> getPieces();
}
