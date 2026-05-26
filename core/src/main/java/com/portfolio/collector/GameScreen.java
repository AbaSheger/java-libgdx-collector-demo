package com.portfolio.collector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private static final float WORLD_WIDTH = 800f;
    private static final float WORLD_HEIGHT = 480f;
    private static final float PLAYER_START_X = 80f;
    private static final float PLAYER_START_Y = 80f;

    private final FitViewport viewport;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Player player;
    private final List<Enemy> enemies;
    private final Collectible collectible;

    private int score;

    public GameScreen() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.4f);

        player = new Player(PLAYER_START_X, PLAYER_START_Y);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(360f, 120f, 34f, 135f, 95f));
        enemies.add(new Enemy(600f, 320f, 42f, -110f, 125f));

        collectible = new Collectible(400f, 240f);
        collectible.respawn(WORLD_WIDTH, WORLD_HEIGHT, player.getBounds(), enemies);
    }

    @Override
    public void render(float delta) {
        update(Math.min(delta, 1f / 30f));
        draw();
    }

    private void update(float delta) {
        player.update(delta, WORLD_WIDTH, WORLD_HEIGHT);

        for (Enemy enemy : enemies) {
            enemy.update(delta, WORLD_WIDTH, WORLD_HEIGHT);
        }

        if (player.getBounds().overlaps(collectible.getBounds())) {
            score++;
            collectible.respawn(WORLD_WIDTH, WORLD_HEIGHT, player.getBounds(), enemies);
        }

        for (Enemy enemy : enemies) {
            if (player.getBounds().overlaps(enemy.getBounds())) {
                resetAfterEnemyHit();
                break;
            }
        }
    }

    private void resetAfterEnemyHit() {
        score = 0;
        player.reset(PLAYER_START_X, PLAYER_START_Y);
        collectible.respawn(WORLD_WIDTH, WORLD_HEIGHT, player.getBounds(), enemies);
    }

    private void draw() {
        ScreenUtils.clear(0.07f, 0.08f, 0.10f, 1f);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawCollectible();
        drawPlayer();
        drawEnemies();
        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "Score: " + score, 24f, WORLD_HEIGHT - 24f);
        font.draw(batch, "Move: WASD or Arrow Keys", 24f, 34f);
        batch.end();
    }

    private void drawPlayer() {
        Rectangle playerBounds = player.getBounds();
        shapeRenderer.setColor(0.20f, 0.75f, 0.95f, 1f);
        shapeRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);
    }

    private void drawEnemies() {
        shapeRenderer.setColor(0.95f, 0.20f, 0.22f, 1f);
        for (Enemy enemy : enemies) {
            Rectangle bounds = enemy.getBounds();
            shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void drawCollectible() {
        Rectangle bounds = collectible.getBounds();
        shapeRenderer.setColor(1f, 0.78f, 0.20f, 1f);
        shapeRenderer.circle(bounds.x + bounds.width / 2f, bounds.y + bounds.height / 2f, bounds.width / 2f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
