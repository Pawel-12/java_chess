package com.chess.model.figures;

import com.chess.model.Board;

public abstract class Figure {
    protected int x;
    protected int y;
    protected char ch;
    protected int color;

    public Figure(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getCh() {
        return ch;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public abstract boolean tryMove(Board board, int x, int y);

    public boolean canMove(Board board, int x, int y) {
        return false;
    }
}
