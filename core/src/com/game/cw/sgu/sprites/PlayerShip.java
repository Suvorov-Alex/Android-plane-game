package com.game.cw.sgu.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class PlayerShip {
    public static int LIFE_COUNT = 1;
    public static int MOVEMENT = 150;

    private static final int GRAVITY = -15;

    private Texture playerShip;
    private Rectangle playerShipRect;

    private Vector3 position;
    private Vector3 velocity; //Скорость распространения

    private Animation playertShipAnimation;

    public PlayerShip(int x, int y) {
        playerShip = new Texture("animationShip.png");
        playertShipAnimation = new Animation(new TextureRegion(playerShip), 3, 0.5f);
        playerShipRect = new Rectangle(x, y, playerShip.getWidth() / 3, playerShip.getHeight());

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        MOVEMENT = 125;
    }

    public void update(float dt) {
        playertShipAnimation.update(dt);
        velocity.add(0, GRAVITY, 0); // add добавляет значение к вектору
        velocity.scl(dt); // Умножаем вектор скорости на скаляр промежутка времени
        position.add(MOVEMENT * dt, velocity.y, 0); // Добавляем новое положение на экране для вертолета
        if (position.y < 0) {
            position.y = 0;
        } // Чтобы не улетать на нижнюю границу экрана

        velocity.scl(1 / dt); // Скорость падения будет увеличиваться с теченим времени

        playerShipRect.setPosition(position.x, position.y);
    }

    public void fly() {
        velocity.y = 300;
    }


    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getPlayerShip() {
        return playertShipAnimation.getFrame();
    }

    public Rectangle getPlayerShipRect() {
        return playerShipRect;
    }

    public void dispose() {
        playerShip.dispose();
    }
}
