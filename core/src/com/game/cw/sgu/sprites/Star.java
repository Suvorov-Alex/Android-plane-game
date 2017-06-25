package com.game.cw.sgu.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Star {
    public static final int WIDTH = 39;

    private Texture star;
    private Vector2 starPos;
    private Rectangle startRect;

    public Star(int x, int y) {
        star = new Texture("starGold.png");
        starPos = new Vector2(x, y);
        startRect = new Rectangle(x, y, star.getWidth(), star.getHeight());
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(startRect);
    }

    public Texture getStar() {
        return star;
    }

    public Vector2 getStarPos() {
        return starPos;
    }

    public Rectangle getStarRect() {
        return startRect;
    }

    public void dispose() {
        star.dispose();
    }
}
