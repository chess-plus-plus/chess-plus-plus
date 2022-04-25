package com.chessplusplus.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

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


    public static void drawBackground(Stage stage){
        stage.getBatch().begin();
        Texture t = new Texture(Gdx.files.internal("background.png"));
        stage.getBatch().draw(t, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        t.dispose();
    }
}
