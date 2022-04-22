package com.chessplusplus.game.utils;

import com.badlogic.gdx.graphics.Pixmap;

public class PixmapUtils {

    /**
     * Draws rectangle to the pixmap with thickness
     * @param width Width of rectangle
     * @param height Height of rectangle
     * @param thickness Border thickness in px
     * @param pixmap the Pixmap to modify
     **/
    public static void drawRectangle(int width, int height, int thickness, Pixmap pixmap) {
        for (int i = 0; i < thickness; i++) {
            pixmap.drawRectangle(i, i, width - i * 2, height - i * 2);
        }
    }
}
