package io.github.CraftedRNL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;
    private FitViewport viewport;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("menu.jpg");
        font = new BitmapFont(Gdx.files.internal("MyFont.fnt"));
        viewport =  new FitViewport(1920, 1080f);
        font.getData().scale(1.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        batch.end();


        batch.begin();
        float graphicsHeight = Gdx.graphics.getHeight();
        batch.setProjectionMatrix(batch.getProjectionMatrix()
            .setToOrtho2D(0, 0, Gdx.graphics.getWidth(), graphicsHeight));
        font.setColor(Color.WHITE);

        font.draw(batch, "Physics Crank Game", (float) viewport.getWorldWidth()/2.5f -1f, viewport.getWorldHeight()/1.5f);
        font.draw(batch, "Click anywhere to start", (float) viewport.getWorldWidth()/2.5f-1f, viewport.getWorldHeight()/1.5f-100);
        batch.end();



        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
    }
}
