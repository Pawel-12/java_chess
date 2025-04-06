package com.chess.model.figures;

import com.chess.model.Board;
import org.apache.commons.lang3.tuple.MutablePair;

public class Rook extends Figure {
    public Rook(int x, int y, int color) {
        super(x, y, color);

        if (color == 0)
            ch = '♖';
        else
            ch = '♜';
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

        if (x == this.x) {
            int start, finish;

            if (this.y < y) {
                start = this.y + 1;
                finish = y;
            } else {
                start = y + 1;
                finish = this.y;
            }

            for (int i = start; i < finish; i++)
                if (board.getFigures().get(MutablePair.of(x, i)) != null)
                    return false;

            return true;
        }

        if (y == this.y) {
            int start, finish;

            if (this.x < x) {
                start = this.x + 1;
                finish = x;
            } else {
                start = x + 1;
                finish = this.x;
            }

            for (int i = start; i < finish; i++)
                if (board.getFigures().get(MutablePair.of(i, y)) != null)
                    return false;

            return true;
        }

        return false;
    }
}
