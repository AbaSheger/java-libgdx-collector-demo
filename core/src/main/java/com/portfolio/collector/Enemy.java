package com.portfolio.collector;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Rectangle bounds;
    private final Vector2 velocity;

    public Enemy(float x, float y, float size, float velocityX, float velocityY) {
        bounds = new Rectangle(x, y, size, size);
        velocity = new Vector2(velocityX, velocityY);
    }

    public void update(float delta, float worldWidth, float worldHeight) {
        bounds.x += velocity.x * delta;
        bounds.y += velocity.y * delta;

        if (bounds.x <= 0f || bounds.x + bounds.width >= worldWidth) {
            velocity.x *= -1f;
            bounds.x = Math.max(0f, Math.min(bounds.x, worldWidth - bounds.width));
        }

        if (bounds.y <= 0f || bounds.y + bounds.height >= worldHeight) {
            velocity.y *= -1f;
            bounds.y = Math.max(0f, Math.min(bounds.y, worldHeight - bounds.height));
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
