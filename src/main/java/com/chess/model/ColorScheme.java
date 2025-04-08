package com.chess.model;

import com.badlogic.gdx.graphics.Color;

public enum ColorScheme {
    Default(new Color(0.41F, 0.22F, 0.04F, 1),
            new Color(0.99F, 0.91F, 0.69F, 1),
            new Color(0, 0, 0, 1),
            new Color(0F, 0.60F, 0.04F, 1),
            new Color(0.41F, 0.50F, 0.05F, 1)
    ),
    Green(new Color(0.03F, 0.21F, 0.02F, 1),
            new Color(0.08F, 0.42F, 0.07F, 1),
            new Color(0.05F, 0.31F, 0.04F, 1),
            new Color(0.08F, 0.36F, 0.07F, 1),
            new Color(0.10F, 0.58F, 0.08F, 1)

    );

    public final Color BlackTile;
    public final Color WhiteTile;
    public final Color Font;
    public final Color MousePointer;
    public final Color PossibleMove;

    ColorScheme(Color blackTile, Color whiteTile, Color font, Color mousePointer, Color possibleMove) {
        this.BlackTile = blackTile;
        this.WhiteTile = whiteTile;
        this.Font = font;
        this.MousePointer = mousePointer;
        this.PossibleMove = possibleMove;
    }
}
