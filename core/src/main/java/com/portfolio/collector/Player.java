package com.portfolio.collector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private static final float SIZE = 32f;
    private static final float SPEED = 240f;

    private final Rectangle bounds;

    public Player(float x, float y) {
        bounds = new Rectangle(x, y, SIZE, SIZE);
    }

    public void update(float delta, float worldWidth, float worldHeight) {
        float moveX = 0f;
        float moveY = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += 1f;
        }

        if (moveX != 0f || moveY != 0f) {
            float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
            bounds.x += (moveX / length) * SPEED * delta;
            bounds.y += (moveY / length) * SPEED * delta;
        }

        bounds.x = MathUtils.clamp(bounds.x, 0f, worldWidth - bounds.width);
        bounds.y = MathUtils.clamp(bounds.y, 0f, worldHeight - bounds.height);
    }

    public void reset(float x, float y) {
        bounds.setPosition(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
