package com.game.cw.sgu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.game.cw.sgu.TappyPlaneCW;

public class MenuState extends State {

    private Texture background;
    private Texture startText;
    private Texture startButton;
    private Rectangle startButtonRect;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");
        startText = new Texture("textGetReady.png");
        startButton = new Texture("buttonStart2.png");
        startButtonRect = new Rectangle(TappyPlaneCW.WIDTH / 2 - startButton.getWidth() / 2,
                TappyPlaneCW.HEIGHT / 2 - 75, startButton.getWidth(), startButton.getHeight());

        camera.setToOrtho(false, TappyPlaneCW.WIDTH, TappyPlaneCW.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            //Rectangle touchRect = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 5, 5);
            //if (touchRect.overlaps(startButtonRect)) {
                gameStateManager.set(new PlayState(gameStateManager));
            //}
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
        spriteBatch.draw(startText, TappyPlaneCW.WIDTH / 2 - startText.getWidth() / 2,
                TappyPlaneCW.HEIGHT / 2 + 20);
        spriteBatch.draw(startButton, TappyPlaneCW.WIDTH / 2 - startButton.getWidth() / 2,
                TappyPlaneCW.HEIGHT / 2 - 75);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        startText.dispose();
    }
}