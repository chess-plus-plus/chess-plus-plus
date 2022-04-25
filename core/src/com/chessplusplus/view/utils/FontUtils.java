package com.chessplusplus.view.utils;

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
        return getGlyph(text, bitmapFont).height;
    }

    /**
     * Calculates width (in pixels) of text in a chosen BitmapFont
     * @param text Text to evaluate width of
     * @param bitmapFont BitmapFont to consider in width calculation
     * @return width in pixels of text in BitmapFont
     * */
    public static float getWidthOfFontText(String text, BitmapFont bitmapFont) {
        return getGlyph(text, bitmapFont).width;
    }

    /**Helper method returning a GlyphLayout based on string text and the bitmapfont
    * @param text Text to evaluate
    * @param bitmapFont BitmapFont to consider in calculation
    * @return GlyphLayout based on text and bitmapFont
    * */
    private static GlyphLayout getGlyph(String text, BitmapFont bitmapFont) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(bitmapFont, text);
        return layout;
    }

}
