package com.chessplusplus.game.component;

import com.badlogic.ashley.core.Component;

public class SizeComponent implements Component {

    public final int width;
    public final int height;

    public SizeComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
