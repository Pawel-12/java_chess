package com.chess.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chess.model.figures.Figure;
import org.apache.commons.lang3.tuple.MutablePair;

import static com.badlogic.gdx.Gdx.input;

public class ChessGame extends ApplicationAdapter implements InputProcessor {
    private int width;
    private int height;

    private int fieldWidth;
    private int fieldHeight;

    private ShapeRenderer shapeRenderer;

    private Board board;
    private BitmapFont font;

    private int turn = 0;
    private boolean pickedUp = false;
    private MutablePair<Integer, Integer> pickedPos;

    private void initGame() {
        this.board = new Board();
        turn = 0;
        pickedUp = false;

        System.out.println("White turn, nr " + (turn + 1));
        System.out.println(board);
    }

    public static ColorScheme colorScheme = ColorScheme.Default;

    public ChessGame(int width, int height) {
        this.width = width;
        this.height = height;

        fieldWidth = width / 8;
        fieldHeight = height / 8;

        initGame();
    }

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        input.setInputProcessor(this);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("dejavu-sans.book.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int) (0.1 * width);
        fontParameter.color = colorScheme.Font;
        if (colorScheme != ColorScheme.Default)
            fontParameter.borderWidth = 3;
        if (colorScheme == ColorScheme.BlackWhite)
            fontParameter.borderColor = Color.WHITE;
        fontParameter.characters = "♖♘♗♕♔♙♜♞♝♛♚♟";

        font = fontGenerator.generateFont(fontParameter);

        fontGenerator.dispose();
    }

    @Override
    public void render() {
        board.drawBoard(shapeRenderer, fieldWidth, fieldHeight);
        mouseMoved(input.getX(), input.getY());
        board.drawFigures(fieldWidth, fieldHeight, font);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.R:
                initGame();
                break;
            case Input.Keys.NUM_1:
                colorScheme = ColorScheme.Default;
                create();
                break;
            case Input.Keys.NUM_2:
                colorScheme = ColorScheme.Green;
                create();
                break;
            case Input.Keys.NUM_3:
                colorScheme = ColorScheme.Dark;
                create();
                break;
            case Input.Keys.NUM_4:
                colorScheme = ColorScheme.BlackWhite;
                create();
                break;
            case Input.Keys.NUM_5:
                colorScheme = ColorScheme.Grey;
                create();
                break;
            case Input.Keys.NUM_6:
                colorScheme = ColorScheme.CGA1;
                create();
                break;
            case Input.Keys.NUM_7:
                colorScheme = ColorScheme.CGA2;
                create();
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int x = screenX / fieldWidth;
        int y = screenY / fieldHeight;

        if ((x > 7) || (x < 0) || (y > 7) || (y < 0))
            return true;

        if (!pickedUp && button == Input.Buttons.LEFT) {
            Figure fig = board.getFigures().get(MutablePair.of(x, y));

            if (fig != null && fig.getColor() == (turn % 2)) {
                pickedPos = new MutablePair<>(x, y);
                pickedUp = true;
                System.out.println("Picked up " + fig.getCh() + " " + pickedPos);
            }

        } else {
            if (button == Input.Buttons.RIGHT) {
                pickedUp = false;
                pickedPos = null;
                return true;
            }

            Figure fig = board.getFigures().get(pickedPos);
            if (pickedPos.equals(MutablePair.of(x, y)) || !fig.tryMove(board, x, y))
                return true;

            Figure temp = board.getFigures().get(MutablePair.of(x, y));
            if (temp != null && (temp.getCh() == '♔' || temp.getCh() == '♚')) {
                System.out.println(temp.getColor() == 0 ? "Black won" : "White won");
                Gdx.app.exit();
                return true;
            }

            board.getFigures().put(MutablePair.of(x, y), fig);
            board.getFigures().remove(pickedPos);
            pickedUp = false;
            System.out.println("Put down " + board.getFigures().get(MutablePair.of(x, y)).getCh() + " " + MutablePair.of(x, y) + "\n");

            turn++;

            if (turn % 2 == 0)
                System.out.println("White turn, nr " + (turn + 1));
            else
                System.out.println("Black turn, nr " + (turn + 1));

            System.out.println(board);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        board.mouseOver(shapeRenderer, screenX, screenY, fieldWidth, fieldHeight);
        if (pickedUp) {
            board.drawPossibleMoves(shapeRenderer, pickedPos, fieldWidth, fieldHeight);

            SpriteBatch batch = new SpriteBatch();
            Figure fig = board.getFigures().get(pickedPos);

            batch.begin();
            font.draw(batch, "" + fig.getCh(), fieldWidth * (screenX / fieldWidth) + (int) (fieldWidth * 0.25), height - (fieldHeight * (screenY / fieldHeight)) - (int) (fieldHeight * 0.25));
            batch.end();
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
