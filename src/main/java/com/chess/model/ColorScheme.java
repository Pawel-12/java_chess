package com.chess.model;

import com.badlogic.gdx.graphics.Color;

public enum ColorScheme {
    Default(new Color(0.41F, 0.22F, 0.04F, 1),
            new Color(0.99F, 0.91F, 0.69F, 1),
            new Color(0, 0, 0, 1),
            new Color(0, 0.60F, 0.04F, 1),
            new Color(0.41F, 0.50F, 0.05F, 1)
    ),
    Green(new Color(0.03F, 0.21F, 0.02F, 1),
            new Color(0.08F, 0.36F, 0.07F, 1),
            new Color(0.0745F, 0.4431F, 0.0549F, 1),
            new Color(0.0941F, 0.5137F, 0.0863F, 1),
            new Color(0.1098F, 0.6F, 0.1020F, 1)
    ),
    Dark(new Color(0, 0, 0, 1),
            new Color(0.0902F, 0.0902F, 0.0902F, 1),
            new Color(0.2510F, 0.2510F, 0.2510F, 1),
            new Color(0.3647F, 0.3647F, 0.3647F, 1),
            new Color(0.4549F, 0.4549F, 0.4549F, 1)
    ),
    BlackWhite(new Color(0, 0, 0, 1),
            new Color(1F, 1F, 1F, 1),
            new Color(0, 0, 0, 1),
            new Color(0.3647F, 0.3647F, 0.3647F, 1),
            new Color(0.4549F, 0.4549F, 0.4549F, 1)
    ),
    Grey(new Color(0, 0, 0, 1),
            new Color(0.6235F, 0.6235F, 0.6235F, 1),
            new Color(0.6235F, 0.6235F, 0.6235F, 1),
            new Color(0.3647F, 0.3647F, 0.3647F, 1),
            new Color(0.4549F, 0.4549F, 0.4549F, 1)
    ),
    CGA1(new Color(0, 0, 0, 1),
            new Color(0.0510F, 0.6824F, 0.6157F, 1),
            new Color(0.5765F, 0, 0.6000F, 1),
            new Color(0.5765F, 0, 0.6000F, 1),
            new Color(0.6235F, 0.6235F, 0.6235F, 1)
    ),
    CGA2(new Color(0, 0, 0, 1),
            new Color(0.0431F, 0.7333F, 0.0196F, 1),
            new Color(0.0431F, 0.7333F, 0.0196F, 1),
            new Color(0.5647F, 0, 0, 1),
            new Color(0.5882F, 0.2745F, 0.0078F, 1)
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
