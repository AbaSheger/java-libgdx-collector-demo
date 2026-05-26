package com.portfolio.collector;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class Collectible {
    private static final float SIZE = 24f;
    private static final float PADDING = 32f;

    private final Rectangle bounds;
    private final RandomXS128 random;

    public Collectible(float x, float y) {
        bounds = new Rectangle(x, y, SIZE, SIZE);
        random = new RandomXS128();
    }

    public void respawn(float worldWidth, float worldHeight, Rectangle playerBounds, List<Enemy> enemies) {
        for (int attempt = 0; attempt < 30; attempt++) {
            bounds.x = PADDING + random.nextFloat() * (worldWidth - SIZE - PADDING * 2f);
            bounds.y = PADDING + random.nextFloat() * (worldHeight - SIZE - PADDING * 2f);

            if (!bounds.overlaps(playerBounds) && !overlapsEnemy(enemies)) {
                return;
            }
        }
    }

    private boolean overlapsEnemy(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (bounds.overlaps(enemy.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
