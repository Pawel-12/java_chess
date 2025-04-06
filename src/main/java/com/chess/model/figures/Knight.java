package com.chess.model.figures;

import com.chess.model.Board;
import org.apache.commons.lang3.tuple.MutablePair;

public class Knight extends Figure {
    public Knight(int x, int y, int color) {
        super(x, y, color);

        if (color == 0)
            ch = '♘';
        else
            ch = '♞';
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

        if ((Math.abs(this.x - x) == 1) && (Math.abs(this.y - y) == 2))
            return true;

        if ((Math.abs(this.x - x) == 2) && (Math.abs(this.y - y) == 1))
            return true;

        return false;
    }
}
