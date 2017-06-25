package com.game.cw.sgu.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.net.ssl.internal.www.protocol.https.Handler;

import java.util.Random;

public class PowerUp {
    private Texture powerUp;
    private Vector2 powerUpPos;
    private Rectangle powerUpRect;
    private Random randomPowerUp;
    private boolean powerUpType;

    public PowerUp(int x, int y) {
        randomPowerUp = new Random();
        powerUpType = randomPowerUp.nextBoolean();
        if (powerUpType) {
            powerUp = new Texture("speedPowerUp.png");
        } else {
            powerUp = new Texture("life.png");
        }

        powerUpPos = new Vector2(x, y);
        powerUpRect = new Rectangle(x, y, powerUp.getWidth(), powerUp.getHeight());
    }

    public void powerUpEffect() {
        if (powerUpType) {
            PlayerShip.MOVEMENT += 150;
        } else {
            PlayerShip.LIFE_COUNT++;
        }
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(powerUpRect);
    }

    public Texture getPowerUp() {
        return powerUp;
    }

    public Vector2 getPowerUpPos() {
        return powerUpPos;
    }

    public Rectangle getPowerUpRect() {
        return powerUpRect;
    }

    public void dispose() {
        powerUp.dispose();
    }
}
