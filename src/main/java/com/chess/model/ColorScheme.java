package com.chess.model;

import com.badlogic.gdx.graphics.Color;

public enum ColorScheme {
    BlackTile(new Color(0.41F, 0.22F, 0.04F, 1)),
    WhiteTile(new Color(0.99F, 0.91F, 0.69F, 1)),
    Font(new Color(0, 0, 0, 1)),
    MousePointer(new Color(0F, 0.60F, 0.04F, 1)),
    PossibleMove(new Color(0.41F, 0.50F, 0.05F, 1));

/*    BlackTile(new Color(0.03F, 0.21F, 0.02F, 1)),
    WhiteTile(new Color(0.08F, 0.42F, 0.07F, 1)),
    Font(new Color(0.05F, 0.31F, 0.04F, 1)),
    MousePointer(new Color(0.08F, 0.36F, 0.07F, 1)),
    PossibleMove(new Color(0.10F, 0.58F, 0.08F, 1));*/

    private final Color color;

    ColorScheme(Color color) {
        this.color = color;
    }

    public Color get() {
        return color;
    }
}
