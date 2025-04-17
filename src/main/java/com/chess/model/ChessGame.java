package com.chess.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chess.model.figures.Figure;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.Gdx.input;

public class ChessGame extends ApplicationAdapter implements InputProcessor {
    enum GameState {Menu, Playing, PVE, EVE, Finished}

    public static ColorScheme colorScheme = ColorScheme.Default;
    private int width;
    private int height;

    private int fieldWidth;
    private int fieldHeight;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private Board board;
    private BitmapFont font;
    private BitmapFont fontWhite;

    private int turn = 0;
    private boolean pickedUp = false;
    private MutablePair<Integer, Integer> pickedPos;
    private GameState gameState;
    private float deltaSum = 0.0F;
    private ArrayList<Button> buttons = new ArrayList<>();
    private float botMoveDelta = 0.0F;
    private int botColor = 1;

    private void initGame() {
        this.board = new Board();
        turn = 0;
        pickedUp = false;
        pickedPos = null;
        deltaSum = 0.0F;
        botMoveDelta = 0.0F;
        gameState = GameState.Playing;

        System.out.println("White turn, nr " + (turn + 1));
        System.out.println(board);
    }

    private void reloadColorsFonts() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("dejavu-sans.book.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int) (0.1 * width);
        fontParameter.color = colorScheme.Font;
        if (colorScheme != ColorScheme.Default)
            fontParameter.borderWidth = 3;
        if (colorScheme == ColorScheme.BlackWhite)
            fontParameter.borderColor = Color.WHITE;
        fontParameter.characters = "♖♘♗♕♔♙♜♞♝♛♚♟ABCEHIKLNOPRSTVW";

        font = fontGenerator.generateFont(fontParameter);

        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.BLACK;
        fontWhite = fontGenerator.generateFont(fontParameter);

        fontGenerator.dispose();
    }

    public ChessGame(int width, int height) {
        this.width = width;
        this.height = height;

        fieldWidth = width / 8;
        fieldHeight = height / 8;

        gameState = GameState.Menu;
    }

    @Override
    public void create() {
        batch = new SpriteBatch(10);
        board = new Board();
        shapeRenderer = new ShapeRenderer();
        input.setInputProcessor(this);
        reloadColorsFonts();

        fontWhite.setColor(Color.FIREBRICK);
        buttons.add(new Button((0.75F * width) / 2, (0.55F * height), 0.25F * width, 0.1F * height, fontWhite, "PVP"));
        buttons.add(new Button((0.75F * width) / 2, (0.44F * height), 0.25F * width, 0.1F * height, fontWhite, "PVE"));
        buttons.add(new Button((0.75F * width) / 2, (0.33F * height), 0.25F * width, 0.1F * height, fontWhite, "EVE"));

        buttons.add(new Button((0.90F * width) / 2, (0.22F * height), 0.1F * width, 0.1F * height, fontWhite, "♙"));
        buttons.get(3).setFillColor(Color.WHITE);
        buttons.get(3).setBorderRatio(0.05F);
        fontWhite.setColor(Color.WHITE);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1, true);

        switch (gameState) {
            case Menu:
                board.drawBoard(shapeRenderer, fieldWidth, fieldHeight);
                buttons.forEach(b -> b.draw(shapeRenderer));

                batch.begin();
                GlyphLayout layout1 = new GlyphLayout(fontWhite, "CHESS");
                fontWhite.draw(batch, "CHESS", (width - layout1.width) / 2.0F, (0.9F * height - layout1.height));
                batch.end();
                break;

            case Playing:
                board.drawBoard(shapeRenderer, fieldWidth, fieldHeight);
                mouseMoved(input.getX(), input.getY());
                board.drawFigures(fieldWidth, fieldHeight, font);
                break;

            case PVE:
                board.drawBoard(shapeRenderer, fieldWidth, fieldHeight);
                board.drawFigures(fieldWidth, fieldHeight, font);

                if (turn % 2 != botColor) {
                    mouseMoved(input.getX(), input.getY());

                } else if ((botMoveDelta += Gdx.graphics.getDeltaTime()) > 2.0F) {
                    botMove(botColor);
                    botMoveDelta = 0.0F;
                }
                break;

            case EVE:
                board.drawBoard(shapeRenderer, fieldWidth, fieldHeight);
                board.drawFigures(fieldWidth, fieldHeight, font);

                if ((botMoveDelta += Gdx.graphics.getDeltaTime()) > 2.0F) {
                    botMove(turn % 2);
                    botMoveDelta = 0.0F;
                }
                break;

            case Finished:
                batch.begin();

                GlyphLayout layout = new GlyphLayout(fontWhite, turn % 2 == 0 ? "♛WHITE WON♛" : "♛BLACK WON♛");

                fontWhite.draw(batch, layout, (width - layout.width) / 2.0F, (height + layout.height) / 2.0F);
                batch.end();

                if ((deltaSum += Gdx.graphics.getDeltaTime()) > 2.0F) {
                    gameState = GameState.Menu;
                    deltaSum = 0.0F;
                }
                break;
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        fontWhite.dispose();
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
                gameState = GameState.Menu;
                break;
            case Input.Keys.NUM_1:
                colorScheme = ColorScheme.Default;
                break;
            case Input.Keys.NUM_2:
                colorScheme = ColorScheme.Green;
                break;
            case Input.Keys.NUM_3:
                colorScheme = ColorScheme.Dark;
                break;
            case Input.Keys.NUM_4:
                colorScheme = ColorScheme.BlackWhite;
                break;
            case Input.Keys.NUM_5:
                colorScheme = ColorScheme.Grey;
                break;
            case Input.Keys.NUM_6:
                colorScheme = ColorScheme.CGA1;
                break;
            case Input.Keys.NUM_7:
                colorScheme = ColorScheme.CGA2;
                break;
        }
        reloadColorsFonts();
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (gameState) {
            case Menu:
                if (buttons.get(0).pointInside(screenX, screenY) && button == Input.Buttons.LEFT)
                    initGame();
                if (buttons.get(1).pointInside(screenX, screenY) && button == Input.Buttons.LEFT) {
                    initGame();
                    gameState = GameState.PVE;
                }
                if (buttons.get(2).pointInside(screenX, screenY) && button == Input.Buttons.LEFT) {
                    initGame();
                    gameState = GameState.EVE;
                }
                if (buttons.get(3).pointInside(screenX, screenY) && button == Input.Buttons.LEFT) {
                    botColor = botColor == 1 ? 0 : 1;
                    if (botColor == 0)
                        buttons.get(3).setFillColor(Color.BLACK);
                    else
                        buttons.get(3).setFillColor(Color.WHITE);
                }
                break;

            case PVE:
                if ((turn % 2) == botColor)
                    break;
            case Playing:
                int x = screenX / fieldWidth;
                int y = screenY / fieldHeight;

                // position outside board
                if ((x > 7) || (x < 0) || (y > 7) || (y < 0))
                    return true;

                // pick up
                if (!pickedUp && button == Input.Buttons.LEFT) {
                    Figure fig = board.getFigures().get(MutablePair.of(x, y));

                    if (fig != null && fig.getColor() == (turn % 2)) {
                        pickedPos = new MutablePair<>(x, y);
                        pickedUp = true;
                        System.out.println("Picked up " + fig.getCh() + " " + pickedPos);
                    }

                } else {
                    // put down
                    if (button == Input.Buttons.RIGHT) {
                        pickedUp = false;
                        pickedPos = null;
                        return true;
                    }

                    // same position or not valid move
                    Figure fig = board.getFigures().get(pickedPos);
                    if (pickedPos.equals(MutablePair.of(x, y)) || !fig.tryMove(board, x, y))
                        return true;

                    return afterMoveUpdate(fig, x, y);
                }
                break;
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
        switch (gameState) {
            case PVE:
                if ((turn % 2) == botColor)
                    break;
            case Playing:
                board.mouseOver(shapeRenderer, screenX, screenY, fieldWidth, fieldHeight);
                if (pickedUp) {
                    board.drawPossibleMoves(shapeRenderer, pickedPos, fieldWidth, fieldHeight);

                    Figure fig = board.getFigures().get(pickedPos);

                    batch.begin();
                    font.draw(batch, "" + fig.getCh(), fieldWidth * (screenX / fieldWidth) + (int) (fieldWidth * 0.25), height - (fieldHeight * (screenY / fieldHeight)) - (int) (fieldHeight * 0.25));
                    batch.end();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private void botMove(int color) {
        var figs = board.getFigures().values();
        ArrayList<MutablePair<Figure, ArrayList<MutablePair<Integer, Integer>>>> possibleMoves = new ArrayList<>();

        for (var f : figs) {
            if (f.getColor() == color)
                if (!board.getPossibleMoves(f).isEmpty())
                    possibleMoves.add(new MutablePair<>(f, board.getPossibleMoves(f)));
        }
        Random rand = new Random();
        var figPair = possibleMoves.get(rand.nextInt(possibleMoves.size()));
        var pos = figPair.right.get(rand.nextInt(figPair.right.size()));

        pickedPos = new MutablePair<>(figPair.left.getX(), figPair.left.getY());
        figPair.left.tryMove(board, pos.left, pos.right);


        afterMoveUpdate(figPair.left, pos.left, pos.right);
    }

    private boolean afterMoveUpdate(Figure fig, int x, int y) {
        Figure temp = board.getFigures().get(MutablePair.of(x, y));
        if (temp != null && (temp.getCh() == '♔' || temp.getCh() == '♚')) {
            System.out.println(temp.getColor() == 0 ? "Black won" : "White won");
            gameState = GameState.Finished;
            return true;
        }

        // update figures map
        board.getFigures().put(MutablePair.of(x, y), fig);
        board.getFigures().remove(pickedPos);
        pickedUp = false;
        System.out.println("Put down " + board.getFigures().get(MutablePair.of(x, y)).getCh() + " " + MutablePair.of(x, y) + "\n");

        turn++;

        System.out.println((turn % 2 == 0 ? "White" : "Black") + " turn, nr " + (turn + 1));
        System.out.println(board);

        return true;
    }
}
