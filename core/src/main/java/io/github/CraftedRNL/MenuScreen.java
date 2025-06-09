package io.github.CraftedRNL;
//lots of imports needed cuz of the methods and classes
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

//menu screen which implements Screen (no clue tbh)
public class MenuScreen implements Screen {
    //game variable so that the methods and data from MAIN can be accessed
    private final Main game;
    //GUI components
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;
    private FitViewport viewport;
    //constructor, gets the Main game
    public MenuScreen(Main game) {

        this.game = game;
    }
    //called when this screen becomes the current screen
    @Override
    public void show() {
        //initializes the variables
        batch = new SpriteBatch();
        //loading assets
        background = new Texture("menu.jpg");//backround images
        font = new BitmapFont(Gdx.files.internal("MyFont.fnt"));//custom font
        //basically the resolution, how big the playing window is
        viewport =  new FitViewport(1920, 1080f);
        //scales font size
        font.getData().scale(1.5f);
    }
//called every frame
    @Override
    public void render(float delta) {
        //clear screen with color black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update viewport
        viewport.apply();
        //draw the background

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        //draws the text
        batch.begin();
        float graphicsHeight = Gdx.graphics.getHeight();
        //Honestly don't know what these do, just know that their required
        batch.setProjectionMatrix(batch.getProjectionMatrix()
            .setToOrtho2D(0, 0, Gdx.graphics.getWidth(), graphicsHeight));
        font.setColor(Color.WHITE);
        // draws the title and instruction, centered
        font.draw(batch, "Physics Crank Game", (float) viewport.getWorldWidth()/2.5f -1f, viewport.getWorldHeight()/1.5f);
        font.draw(batch, "Click anywhere to start", (float) viewport.getWorldWidth()/2.5f-1f, viewport.getWorldHeight()/1.5f-100);
        batch.end();


        // if the screen is clicked, starts the game
        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        }
    }
    //everything after is kinda needed by the class
//called when window is resized
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
//prevents memory leaks
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
    }
}
