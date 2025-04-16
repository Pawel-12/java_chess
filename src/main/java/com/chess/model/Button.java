package com.chess.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
    private float x;
    private float y;
    private float width;
    private float height;
    private GlyphLayout glyphs;
    private BitmapFont font;
    private Color fillColor = new Color(0.0941F, 0.2431F, 0.0471F, 1);
    private SpriteBatch batch;

    public Button(float x, float y, float width, float height, BitmapFont font, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        batch = new SpriteBatch(5);

        glyphs = new GlyphLayout(font, text);
    }

    public boolean pointInside(float xp, float yp) {
        // mouse click event uses left top corner as origin, drawing uses bottom
        xp = Gdx.app.getGraphics().getWidth() - xp;
        yp = Gdx.app.getGraphics().getHeight() - yp;

        boolean xRange = (xp >= x) && (xp <= (x + width));
        boolean yRange = (yp >= y) && (yp <= (y + height));

        return xRange && yRange;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(fillColor);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.set((ShapeRenderer.ShapeType.Line));
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glLineWidth(0.02F * width);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, glyphs, x + ((width - glyphs.width) / 2), (y + height) - ((height - glyphs.height) / 2));
        batch.end();
    }
}