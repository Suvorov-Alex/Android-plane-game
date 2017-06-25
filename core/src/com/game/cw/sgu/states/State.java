package com.game.cw.sgu.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera camera; // Ортографиечская камера - окно в игровой мир
    protected Vector3 mouse; // класс 3D проекции. Исполь-ся 3D т.к Ортографическая камера поддерживает 3D, => коорд. Z = 0
    protected GameStateManager gameStateManager;

    public State(GameStateManager gsm) {
        this.gameStateManager = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();

    public abstract void update(float dt); // Обновляет картинку через определенные промежутки времени

    public abstract void render(SpriteBatch spriteBatch); // Рисовать экран
    // Sprite Batch предсставляет текстуру и координату для рисования фигур

    public abstract void dispose();
}
