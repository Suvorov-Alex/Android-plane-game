package com.game.cw.sgu.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Rock {
    public static final int WIDTH = 108;

    private Texture rock;
    private Vector2 rockPos;
    private Rectangle rockRect;

    private Random random;

    public Rock(float x) {
        random = new Random();
        boolean rockPos = random.nextBoolean();
        if (rockPos) {
            rock = new Texture("rockTop.png");
            this.rockPos = new Vector2(x, rock.getHeight() + 5);
            rockRect = new Rectangle(this.rockPos.x + 45, this.rockPos.y + 10,
                    rock.getWidth() - 57, rock.getHeight());
        } else {
            rock = new Texture("rockBottom.png");
            this.rockPos = new Vector2(x, -5);
            rockRect = new Rectangle(this.rockPos.x + 45, this.rockPos.y,
                    rock.getWidth() - 57, rock.getHeight() - 30);
        }
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(rockRect);
    }

    public Texture getRock() {
        return rock;
    }

    public Vector2 getRockPos() {
        return rockPos;
    }

    public Rectangle getRockRect() {
        return rockRect;
    }

    public void removeRect() {
        rockRect = new Rectangle(-100, 0, 0, 0);
    }

    public void dispose() {
        rock.dispose();
    }
}
