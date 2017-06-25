package com.game.cw.sgu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.cw.sgu.TappyPlaneCW;

public class GameOverState extends State {
    private int score;
    private int bestScore;
    private Preferences preferences;

    private Texture background;
    private Texture textGameOver;

    private BitmapFont font;

    public GameOverState(GameStateManager gsm, int score) {
        super(gsm);

        background = new Texture("background.png");
        textGameOver = new Texture("textGameOver.png");

        setScores(score);

        font = new BitmapFont();
        font.getData().setScale(2.0f);

        camera.setToOrtho(false, TappyPlaneCW.WIDTH, TappyPlaneCW.HEIGHT);
    }

    private void setScores(int score) {
        this.score = score;
        preferences = Gdx.app.getPreferences("scorePrefs");
        if (preferences.contains("bestScore")) {
            bestScore = preferences.getInteger("bestScore");
            if (bestScore < score) {
                bestScore = score;
                preferences.remove("bestScore");
                preferences.putInteger("bestScore", bestScore);
            }
        } else {
            bestScore = score;
            preferences.putInteger("bestScore", bestScore);
        }
        preferences.flush();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0);
        spriteBatch.draw(textGameOver, TappyPlaneCW.WIDTH / 2 - textGameOver.getWidth() / 2,
                TappyPlaneCW.HEIGHT / 2 + 30);
        font.draw(spriteBatch, "Score: " + score, TappyPlaneCW.WIDTH / 2 - textGameOver.getWidth() / 2 - 10,
                TappyPlaneCW.HEIGHT / 2 - 20);

        font.draw(spriteBatch, "Best Score: " + bestScore, TappyPlaneCW.WIDTH / 2 - textGameOver.getWidth() / 2 - 10,
                TappyPlaneCW.HEIGHT / 2 - 50);


        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        textGameOver.dispose();
        font.dispose();
    }
}