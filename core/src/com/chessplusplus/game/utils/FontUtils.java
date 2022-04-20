package com.chessplusplus.game.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontUtils {

    /**
     * Calculates height (in pixels) of text in a chosen BitmapFont
     * @param text Text to evaluate height of
     * @param bitmapFont BitmapFont to consider in height calculation
     * @return height in pixels of text in BitmapFont
     * */
    public static float getHeightOfFontText(String text, BitmapFont bitmapFont) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(bitmapFont, text);
        return layout.height;
    }
}
