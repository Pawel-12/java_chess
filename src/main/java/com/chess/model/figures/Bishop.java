package com.chess.model.figures;

import com.chess.model.Board;
import org.apache.commons.lang3.tuple.MutablePair;

public class Bishop extends Figure {
    public Bishop(int x, int y, int color) {
        super(x, y, color);

        if (color == 0)
            ch = '♗';
        else
            ch = '♝';
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        if (canMove(board, x, y)) {
            this.setXY(x, y);
            return true;
        }

        return false;
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (board.getFigures().get(MutablePair.of(x, y)) != null && board.getFigures().get(MutablePair.of(x, y)).getColor() == color)
            return false;

        if ((this.x - this.y) == (x - y)) {

            if (this.x < x) {
                for (int i = this.x + 1, j = this.y + 1; i < x && j < y; i++, j++)
                    if (board.getFigures().get(MutablePair.of(i, j)) != null)
                        return false;

            } else {
                for (int i = this.x - 1, j = this.y - 1; i > x && j > y; i--, j--)
                    if (board.getFigures().get(MutablePair.of(i, j)) != null)
                        return false;
            }

            return true;
        }

        if ((this.x + this.y) == (x + y)) {

            if (this.x < x) {
                for (int i = this.x + 1, j = this.y - 1; i < x && j > y; i++, j--)
                    if (board.getFigures().get(MutablePair.of(i, j)) != null)
                        return false;

            } else {
                for (int i = this.x - 1, j = this.y + 1; i > x && j < y; i--, j++)
                    if (board.getFigures().get(MutablePair.of(i, j)) != null)
                        return false;
            }

            return true;
        }

        return false;
    }
}
