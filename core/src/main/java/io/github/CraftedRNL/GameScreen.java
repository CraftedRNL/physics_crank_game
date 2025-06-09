package io.github.CraftedRNL;
// gotta import everything
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen {
    // game instance
    private final Main game;
    //rendering components (needed to see the stuff)
    private SpriteBatch batch;
    private FitViewport viewport;
    //textures and sprite
    private Texture gearTexture;
    private Texture bulbTexture;
    private Texture litBulbTexture;
    private Texture steampunk;
    private Sprite gearSprite;
    //physics variables
    private float angularVelocity = 0;
    private float momentOfInertia = 21.98f;
    private int appliedForce;
    private float energyThreshold = 60f;
    private float kineticEnergy = 0;
    private final float radius = 1f;
    private final float thickness = 0.02f;
    //Game states || other stuff
    private boolean bulbLit = false;
    private float elapsedTime = 0f;
    private int ePoints;
    private int eMult;
    private int gearCount;
    private Material currentMaterial = Material.WOOD;
    private boolean rotating = false;
    private ScheduledExecutorService executor;
    private BitmapFont font;
    private boolean timerRunning;
    private String wheel ="woodWheel.png";

    // basic constructor
    public GameScreen(Main game) {
        this.game = game;
    }
// constructor for shopScreen
    public GameScreen(Main game, Material changeMaterial) {
        this.game = game;
        this.ePoints = game.getEPoints();
        this.gearCount = game.getGearCount();
        //update material
        game.setMaterial(changeMaterial);
        this.currentMaterial = game.getMaterial();
        //recalculates physics
        updateMaterialProperties();
        updateMaterial(changeMaterial);
        //calculate force based on gearcount
        this.appliedForce = 10 + (gearCount * 10);
    }

    //update material properties
    private void updateMaterialProperties() {

        currentMaterial = game.getMaterial();
        //uses switch case to set properties bsaed on material
        switch (currentMaterial) {
            case WOOD:
                eMult = 1;
                wheel = "woodWheel.png";
                break;
            case PLASTIC:
                eMult = 2;
                wheel = "plasticWheel.png";
                break;
            case IRON:
                eMult = 5;
                wheel = "ironWheel.png";
                break;
            case GOLD:
                eMult = 10;
                wheel = "goldWheel.png";
                break;
            case OSMIUM:
                eMult = 15;
                wheel = "osmiumWheel.png";
                break;
        }
    }
    //transiton to shopScreen
    private void goToShopScreen(){
        //saves states
        game.setEPoints(ePoints);
        game.setGearCount(gearCount);
        //switches
        game.setScreen(new ShopScreen(game));
    }
    public void show(){
        this.gearCount = game.getGearCount();
        appliedForce = 10 + (gearCount * 10);
        //rendering
        batch = new SpriteBatch();
        gearTexture = new Texture(wheel);
        bulbTexture = new Texture("bulb.png");
        viewport = new FitViewport(10,5);
        steampunk = new Texture("steampunk.jpg");
        litBulbTexture = new Texture("litBulb.png");
        //sets wheel sprite
        gearSprite = new Sprite(gearTexture);
        gearSprite.setSize(2,2);
        gearSprite.setOriginCenter();
        //sets font and timer
        font = new BitmapFont(Gdx.files.internal("MyFont.fnt"));
        font.setUseIntegerPositions(false);
        executor = Executors.newSingleThreadScheduledExecutor();
        updateMaterialProperties();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    //loop
    @Override
    public void render(float delta) {

        handleInput();
        updatePhysics();
        updateLogic();
        draw();
    }
    //player input
    public void handleInput() {
        rotating = false;
        //right arrow clockwise torque
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            applyTorque(false);
            rotating = true;
            //right arrow counter-clockwise torque
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            applyTorque(true);
            rotating = true;

        }
        //M to shop
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) goToShopScreen();
        //cheat
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ADD)) ePoints+=10000;

    }
    //force to wheel
    private void applyTorque(boolean clockwise){

        //physics
        float torque = appliedForce * radius;
        float angularAcceleration = torque / momentOfInertia;
        elapsedTime += Gdx.graphics.getDeltaTime();
        //update speed based on direction
        if(clockwise){
            angularVelocity = angularAcceleration * elapsedTime;
        }else{
            angularVelocity = -angularAcceleration * elapsedTime;
        }

    }

    //update physics calc
    public void updatePhysics(){
        //KE = 1/2IW^2
         kineticEnergy = 0.5f * momentOfInertia * angularVelocity * angularVelocity;

//        if not active, apply friction
        if(!rotating) {
            angularVelocity *= 0.99f;
            elapsedTime = 0f;
            //stops compelty under a certain speed
            if(Math.abs(angularVelocity) < 0.05f) {
                angularVelocity = 0f;

            }
        }
    }
    // game logic
    public void updateLogic() {
        //bulb lights up when ke>energy needed
        bulbLit = kineticEnergy >= energyThreshold;

        //start stop timer based on bulb state
        if(bulbLit && !timerRunning) {
            startTimer();
            timerRunning = true;
        }else if(!bulbLit && timerRunning) {
            stopTimer();
            timerRunning = false;
        }
    }
    //start timer
    public void startTimer(){

        executor.scheduleAtFixedRate(() -> {
            // points = (energy/100)*mult
            ePoints+= (int) (kineticEnergy/100 * eMult);
        }, 0, 1, TimeUnit.SECONDS);
    }
    //stop timer
    public void stopTimer(){
        executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();//makes new one, need it since stopped old one
    }
    //update physics when mat changes
    private void updateMaterial(Material material){
//        calculate volume and mass
        float volume = (float)(Math.PI * radius * radius * thickness);
        float mass = material.density * volume;
//        calculates inertia
        momentOfInertia = 0.5f * mass * radius * radius;
        momentOfInertia = Math.round(momentOfInertia * 100f)/100f;// rounds to 2 decimals
    }


//draws everything
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
//background
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        batch.draw(steampunk, 0, 0, worldWidth, worldHeight);
//wheel position
        gearSprite.setPosition(worldWidth/2 - gearSprite.getWidth()/2, 0);
//   converts radians into degrees to rotate better
        gearSprite.rotate(angularVelocity * Gdx.graphics.getDeltaTime() * 180f/(float)Math.PI);
        gearSprite.draw(batch);
//conditional if bulb is lit
        if(bulbLit) {
            batch.draw(litBulbTexture, worldWidth/2 - 0.5f, worldHeight/2 , 1, 1);
        }else {
            batch.draw(bulbTexture, worldWidth/2 - 0.5f, worldHeight/2, 1, 1);
        }

        batch.setColor(Color.WHITE);
        batch.end();

//    UI text
        batch.begin();
        float graphicsHeight = Gdx.graphics.getHeight();
        batch.setProjectionMatrix(batch.getProjectionMatrix()
            .setToOrtho2D(0, 0, Gdx.graphics.getWidth(), graphicsHeight));

//    Game stats
        font.setColor(Color.WHITE);
        font.draw(batch, "Material: " + game.getMaterial().name, 25, graphicsHeight/1.5f - 30);
        font.draw(batch, "E-Points: " + ePoints, 25, graphicsHeight/1.5f-60);
        font.draw(batch, "Force: " + appliedForce, 25, graphicsHeight/1.5f-90);
        font.draw(batch, String.format("Inertia: " + momentOfInertia), 25, graphicsHeight/1.5f-120);
        font.draw(batch, String.format("Omega: %.2f", angularVelocity), 25, graphicsHeight/1.5f-180);
        font.draw(batch, String.format("KE: %.2f", kineticEnergy), 25, graphicsHeight/1.5f - 210);
        font.draw(batch, String.format("Time: %.2f", elapsedTime), 25, graphicsHeight/1.5f-240);
        batch.end();
    }
//prevent memory leak
    @Override
    public void dispose() {
        batch.dispose();
        gearTexture.dispose();
        bulbTexture.dispose();
        steampunk.dispose();
        litBulbTexture.dispose();
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
