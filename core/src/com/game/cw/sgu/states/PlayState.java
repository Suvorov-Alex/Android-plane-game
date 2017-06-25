package com.game.cw.sgu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.utils.Array;
import com.game.cw.sgu.TappyPlaneCW;
import com.game.cw.sgu.sprites.PlayerShip;
import com.game.cw.sgu.sprites.PowerUp;
import com.game.cw.sgu.sprites.Rock;
import com.game.cw.sgu.sprites.Star;

import java.util.Iterator;
import java.util.Random;

public class PlayState extends State {
    private static final int ROCK_SPACING = 300;

    private static final int ROCK_COUNT = 20;
    private static final int POWERUP_COUNT = 3;
    private static final int STAR_COUNT = 50;

    private static String STAR_ACHIVED;

    private BitmapFont font;

    private Music helicopterMusic;
    private Sound crashSound;
    private Sound starSound;
    private Sound powerUpSound;
    private Sound loseLifeSound;

    private Texture background;
    private Texture ground;
    private Vector2 groundPos1;
    private Vector2 groundPos2;
    private Rectangle groundRect;

    private PlayerShip playerShip;
    private Array<Rock> rocks;
    private Array<Star> stars;
    private Array<Rectangle> starsRect;
    private Array<PowerUp> powerUps;
    private Array<Rectangle> powerUpsRect;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.png");
        font = new BitmapFont();
        font.getData().setScale(2.0f, 2.0f);

        playerShip = new PlayerShip(10, 200);

        setGround();
        spawnRocks();
        spawnStars();
        spawnPowerUps();

        camera.setToOrtho(false, TappyPlaneCW.WIDTH, TappyPlaneCW.HEIGHT);

        setSoundsAndMusic();
    }

    private void setSoundsAndMusic() {
        crashSound = Gdx.audio.newSound(Gdx.files.internal("crashSound.mp3"));
        starSound = Gdx.audio.newSound(Gdx.files.internal("star.mp3"));
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerUpSound.mp3"));
        loseLifeSound = Gdx.audio.newSound(Gdx.files.internal("loseLife.wav"));

        helicopterMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        helicopterMusic.setLooping(true);
        helicopterMusic.play();
    }

    private void spawnRocks() {
        rocks = new Array<Rock>();
        for (int i = 1; i <= ROCK_COUNT; i++) {
            rocks.add(new Rock(i * ROCK_SPACING + Rock.WIDTH));
        }
    }

    private void spawnStars() {
        STAR_ACHIVED = "0";
        stars = new Array<Star>();
        starsRect = new Array<Rectangle>();
        for (int i = 0; i <= STAR_COUNT; i++) {
            Star newStar = new Star(i * (new Random().nextInt(135) + 75) + Star.WIDTH,
                    (new Random().nextInt(75) + 215));
            stars.add(newStar);
            starsRect.add(newStar.getStarRect());
        }
    }


    private void spawnPowerUps() {
        powerUps = new Array<PowerUp>();
        powerUpsRect = new Array<Rectangle>();
        for (int i = 0; i < POWERUP_COUNT; i++) {
            PowerUp newPowerUp = new PowerUp((new Random().nextInt(3000) + 600), 230);
            powerUps.add(newPowerUp);
            powerUpsRect.add(newPowerUp.getPowerUpRect());
        }
    }

    private void rocksCollides() {
        for (int i = 0; i < rocks.size; i++) {
            if (rocks.get(i).collides(playerShip.getPlayerShipRect())) {
                if (PlayerShip.LIFE_COUNT > 1) {
                    PlayerShip.LIFE_COUNT--;
                    loseLifeSound.play();
                    rocks.get(i).removeRect();
                } else {
                    crashSound.play();
                    helicopterMusic.stop();
                    gameStateManager.set(new GameOverState(gameStateManager,
                            Integer.parseInt(STAR_ACHIVED)));
                }
            }
        }
    }

    private void starsCollides() {
        Iterator<Rectangle> iterator = starsRect.iterator();
        while (iterator.hasNext()) {
            Rectangle starRect = iterator.next();
            if (starRect.overlaps(playerShip.getPlayerShipRect())) {
                starSound.play();
                iterator.remove();
                for (int i = 0; i < stars.size; i++) {
                    if (stars.get(i).getStarRect().equals(starRect)) {
                        stars.removeRange(i, i);
                        int starAchived = (Integer.parseInt(STAR_ACHIVED) + 1);
                        STAR_ACHIVED = String.valueOf(starAchived);
                    }
                }
            }
        }
    }

    private void powerUpCollides() {
        Iterator<Rectangle> iterator = powerUpsRect.iterator();
        while (iterator.hasNext()) {
            Rectangle powerUpRect = iterator.next();
            if (powerUpRect.overlaps(playerShip.getPlayerShipRect())) {
                iterator.remove();
                for (int i = 0; i < powerUps.size; i++) {
                    if (powerUps.get(i).getPowerUpRect().equals(powerUpRect)) {
                        powerUps.get(i).powerUpEffect();
                        powerUps.removeRange(i, i);
                        powerUpSound.play();
                    }
                }
            }
        }
    }

    private void groundCollides() {
        if (playerShip.getPlayerShipRect().overlaps(groundRect)) {
            crashSound.play();
            helicopterMusic.stop();
            gameStateManager.set(new GameOverState(gameStateManager,
                    Integer.parseInt(STAR_ACHIVED)));
        }
    }

    private void setGround() {
        ground = new Texture("groundGrass.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2 - 300, 0);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth() - 300, 0);
        groundRect = new Rectangle(camera.position.x, 0, TappyPlaneCW.WIDTH, 50);
    }

    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
        groundRect = new Rectangle(camera.position.x, 0, TappyPlaneCW.WIDTH, 50);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            playerShip.fly();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        playerShip.update(dt);
        camera.position.x = playerShip.getPosition().x + 80;
        // очень важно. Чтобы камера двигалась за вертолетом, а не стояла на месте.

        rocksCollides();
        starsCollides();
        powerUpCollides();
        groundCollides();

        camera.update();
    }

    private void disposeOldRocks() {
        for (int i = 0; i < rocks.size; i++) {
            if (rocks.get(i).getRockRect().x < camera.position.x - camera.viewportWidth) {
                rocks.get(i).removeRect();
            }
            if (rocks.get(i).getRockRect().x == -100) {
                rocks.get(i).dispose();
            }
        }
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(background, camera.position.x - (camera.viewportWidth / 2),
                camera.position.y - (camera.viewportHeight / 2));
        // Отриусем бэкграунд, чтобы он располагался по центру обзора камеры (отрисовка BG относительно вертолета)
        spriteBatch.draw(playerShip.getPlayerShip(), playerShip.getPosition().x,
                playerShip.getPosition().y); // Передаем для отрисовки параметры объекта класса PlayerShip

        for (Rock rock : rocks) {
            spriteBatch.draw(rock.getRock(), rock.getRockPos().x, rock.getRockPos().y);
        }

        for (Star star : stars) {
            spriteBatch.draw(star.getStar(), star.getStarPos().x, star.getStarPos().y);
        }

        for (PowerUp powerUp : powerUps) {
            spriteBatch.draw(powerUp.getPowerUp(), powerUp.getPowerUpPos().x,
                    powerUp.getPowerUpPos().y);
        }

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);

        font.draw(spriteBatch, "Score: " + STAR_ACHIVED, camera.position.x - (camera.viewportWidth / 2) + 7, 473);
        font.draw(spriteBatch, "Life: " + PlayerShip.LIFE_COUNT, camera.position.x - (camera.viewportWidth / 2) + 7, 443);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        starSound.dispose();
        crashSound.dispose();
        powerUpSound.dispose();
        loseLifeSound.dispose();
        helicopterMusic.dispose();
        font.dispose();
        playerShip.dispose();
        for (Rock rock : rocks) {
            rock.dispose();
        }
        for (Star star : stars) {
            star.dispose();
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.dispose();
        }
    }
}
