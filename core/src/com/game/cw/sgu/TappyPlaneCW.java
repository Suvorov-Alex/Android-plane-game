package com.game.cw.sgu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.cw.sgu.states.GameStateManager;
import com.game.cw.sgu.states.MenuState;

public class TappyPlaneCW extends ApplicationAdapter {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    private GameStateManager gameStateManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager));
    }

    @Override
    public void render() {
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
