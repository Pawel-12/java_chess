package com.chess.model.figures;

import com.chess.model.Board;
import org.apache.commons.lang3.tuple.MutablePair;

public class Pawn extends Figure {
    private boolean hasMoved = false;

    public Pawn(int x, int y, int color) {
        super(x, y, color);

        if (color == 0)
            ch = '♙';
        else
            ch = '♟';
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        if (canMove(board, x, y)) {
            hasMoved = true;

            this.setXY(x, y);
            return true;
        }

        return false;
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (Math.abs(this.y - y) == 0)
            return false;

        if (board.getFigures().get(MutablePair.of(x, y)) == null) {
            if (color == 0) {
                if ((y < this.y) || (y - this.y) > (1 + (hasMoved ? 0 : 1)))
                    return false;
                if (board.getFigures().get(MutablePair.of(this.x, this.y + 1)) != null)
                    return false;
            } else {
                if ((y > this.y) || (this.y - y) > (1 + (hasMoved ? 0 : 1)))
                    return false;
                if (board.getFigures().get(MutablePair.of(this.x, this.y - 1)) != null)
                    return false;
            }
            if (Math.abs(this.x - x) > 0)
                return false;
        } else {
            if (board.getFigures().get(MutablePair.of(x, y)).getColor() == color)
                return false;
            if (Math.abs(this.x - x) != 1 || (color == 0 ? (y - this.y) != 1 : (this.y - y) != 1))
                return false;
        }

        return true;
    }
}
