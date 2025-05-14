package ru.itschool.spherical_space;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Button {
    String text;
    float x, y;
    float w, h;
    BitmapFont f;


    public Button(BitmapFont f, String text, float x, float y) {
        this.f = f;
        this.text = text;
        this.x = x;
        this.y = y;
        GlyphLayout glyphLayout = new GlyphLayout(f, text);
        w = glyphLayout.width;
        h = glyphLayout.height;
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+w && y>ty && ty>y-h;
    }
}
