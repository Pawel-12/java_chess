package com.chess.model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chess.model.figures.*;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Arrays;
import java.util.Hashtable;

import static com.chess.model.ChessGame.colorScheme;

public class Board {
    private final int sideSize = 8;
    private Hashtable<MutablePair<Integer, Integer>, Figure> figures;

    private char[][] fields;

    public Board() {
        this.figures = new Hashtable<>();

        fields = new char[sideSize][sideSize];

        for (int i = 0; i < sideSize; i++)
            Arrays.fill(fields[i], '.');

        createFigures();
    }

    private void createFigures() {
        enum Color {
            Black(0), White(1);
            private int v;

            Color(int i) {
                v = i;
            }
        }

        for (int i = 0; i < 8; i++) {
            figures.put(new MutablePair<>(i, 1), new Pawn(i, 1, Color.Black.v));
            figures.put(new MutablePair<>(i, sideSize - 2), new Pawn(i, sideSize - 2, Color.White.v));
        }

        figures.put(new MutablePair<>(0, 0), new Rook(0, 0, Color.Black.v));
        figures.put(new MutablePair<>(sideSize - 1, 0), new Rook(sideSize - 1, 0, Color.Black.v));

        figures.put(new MutablePair<>(sideSize - 1, sideSize - 1), new Rook(sideSize - 1, sideSize - 1, Color.White.v));
        figures.put(new MutablePair<>(0, sideSize - 1), new Rook(0, sideSize - 1, Color.White.v));


        figures.put(new MutablePair<>(1, 0), new Knight(1, 0, Color.Black.v));
        figures.put(new MutablePair<>(sideSize - 2, 0), new Knight(sideSize - 2, 0, Color.Black.v));

        figures.put(new MutablePair<>(sideSize - 2, sideSize - 1), new Knight(sideSize - 2, sideSize - 1, Color.White.v));
        figures.put(new MutablePair<>(1, sideSize - 1), new Knight(1, sideSize - 1, Color.White.v));


        figures.put(new MutablePair<>(2, 0), new Bishop(2, 0, Color.Black.v));
        figures.put(new MutablePair<>(sideSize - 3, 0), new Bishop(sideSize - 3, 0, Color.Black.v));

        figures.put(new MutablePair<>(sideSize - 3, sideSize - 1), new Bishop(sideSize - 3, sideSize - 1, Color.White.v));
        figures.put(new MutablePair<>(2, sideSize - 1), new Bishop(2, sideSize - 1, Color.White.v));


        figures.put(new MutablePair<>(3, 0), new Queen(3, 0, Color.Black.v));
        figures.put(new MutablePair<>(sideSize - 4, 0), new King(sideSize - 4, 0, Color.Black.v));

        figures.put(new MutablePair<>(3, sideSize - 1), new Queen(3, sideSize - 1, Color.White.v));
        figures.put(new MutablePair<>(sideSize - 4, sideSize - 1), new King(sideSize - 4, sideSize - 1, Color.White.v));
    }

    @Override
    public String toString() {
        for (int i = 0; i < sideSize; i++)
            Arrays.fill(fields[i], '.');

        StringBuilder sb = new StringBuilder();

        for (Figure f : figures.values())
            fields[f.getY()][f.getX()] = f.getCh();

        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++)
                sb.append(fields[i][j]);
            sb.append('\n');
        }
        return sb.toString();
    }

    public void drawBoard(ShapeRenderer shapeRenderer, int fieldWidth, int fieldHeight) {
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                if (((i % 2) ^ (j % 2)) == 0)
                    shapeRenderer.setColor(colorScheme.BlackTile);
                else
                    shapeRenderer.setColor(colorScheme.WhiteTile);

                shapeRenderer.rect(fieldWidth * i, fieldHeight * j, fieldWidth, fieldHeight);
                shapeRenderer.end();
            }
        }
    }

    public void drawFigures(int fieldWidth, int fieldHeight, BitmapFont font) {
        SpriteBatch batch = new SpriteBatch();

        batch.begin();

        for (Figure f : figures.values())
            font.draw(batch, "" + f.getCh(), (fieldWidth * f.getX()) + (int) (fieldWidth * 0.25), (fieldHeight * 8) - (fieldHeight * f.getY()) - (int) (fieldHeight * 0.25));

        batch.end();
    }

    public void mouseOver(ShapeRenderer shapeRenderer, int x, int y, int fieldWidth, int fieldHeight) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(colorScheme.MousePointer);

        shapeRenderer.rect((x / fieldWidth) * fieldWidth, (7 - (y / fieldHeight)) * fieldHeight, fieldWidth, fieldHeight);
        shapeRenderer.end();
    }

    public void drawPossibleMoves(ShapeRenderer shapeRenderer, MutablePair<Integer, Integer> pos, int fieldWidth, int fieldHeight) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colorScheme.PossibleMove);

        for (int i = 0; i < sideSize; i++)
            for (int j = 0; j < sideSize; j++)
                if (figures.get(pos).canMove(this, i, j))
                    shapeRenderer.rect(i * fieldWidth, (7 - j) * fieldHeight, fieldWidth, fieldHeight);

        shapeRenderer.end();
    }

    public Hashtable<MutablePair<Integer, Integer>, Figure> getFigures() {
        return figures;
    }
}
