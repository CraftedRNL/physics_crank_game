package io.github.CraftedRNL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ShopScreen implements Screen {
    private final Main game;
    private Texture shopBackground;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Stage stage;
    private Skin skin;
    private float gearPrice;
    private final int plasticPrice = 50;
    private final int ironPrice = 500;
    private final int goldPrice = 2500;
    private final int osmiumPrice = 10000;
    private int currentEPoints;
    private int gearCount;
    TextButton woodButton;
    TextButton plasticButton;
    TextButton ironButton;
    TextButton goldButton;
    TextButton osmiumButton;
    TextButton buyGearButton;

    private boolean plasticBought = false;
    private boolean ironBought = false;
    private boolean goldBought = false;
    private boolean osmiumBought = false;

    private Material material;

    public ShopScreen(Main game){
        this.game = game;
        this.gearCount = game.getGearCount();
        this.currentEPoints = game.getEPoints();
        this.gearPrice = calculateGearPrice();

        this.plasticBought = game.isPlasticBought();
        this.ironBought = game.isIronBought();
        this.goldBought = game.isGoldBought();
        this.osmiumBought = game.isOsmiumBought();
        this.material = game.getMaterial();
        System.out.println(gearPrice);
    }
    private float calculateGearPrice() {
        return (game.getGearCount() * 10f)+10f;
    }
    private void updateButtonStates(){
        if (plasticBought) {
            plasticButton.setText("Plastic (Owned)");
        } else {
            plasticButton.setText("Plastic (" + plasticPrice + " E-Points)");
        }

        if (ironBought) {
            ironButton.setText("Iron (Owned)");
        } else {
            ironButton.setText("Iron (" + ironPrice + " E-Points)");
        }

        if (goldBought) {
            goldButton.setText("Gold (Owned)");
        } else {
            goldButton.setText("Gold (" + goldPrice + " E-Points)");
        }

        if (osmiumBought) {
            osmiumButton.setText("Osmium (Owned)");
        } else {
            osmiumButton.setText("Osmium (" + osmiumPrice + " E-Points)");
        }
    }
    public void addGear(){
        if(currentEPoints >= gearPrice){

            currentEPoints -= (int)gearPrice;
            game.setEPoints(currentEPoints);

            System.out.println("G" +gearPrice);

            gearCount++;
            game.setGearCount(gearCount);;
            System.out.println(gearCount);
            gearPrice = calculateGearPrice();

            buyGearButton.setText("Buy Gear. Price:" + gearPrice);

        }
    }
    @Override
    public void show() {
        gearPrice = calculateGearPrice();
        batch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/clean-crispy-ui.json"));


        shopBackground = new Texture(Gdx.files.internal("shop.jpg"));


        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);



         buyGearButton = new TextButton("Buy Gear. Price:" + gearPrice, skin);
         woodButton = new TextButton("Wood", skin);
         plasticButton = new TextButton("Plastic (" + plasticPrice + " E-Points)", skin);
         ironButton = new TextButton("Iron (" + ironPrice + " E-Points)", skin);
         goldButton = new TextButton("Gold (" + goldPrice + " E-Points)", skin);
         osmiumButton = new TextButton("Osmium (" + osmiumPrice + " E-Points)", skin);
        TextButton backButton = new TextButton("Back to Game", skin);


        backButton.addListener(event -> {
            if(Gdx.input.justTouched()){
                game.setMaterial(material);
                game.setEPoints(currentEPoints);
                game.setScreen(new GameScreen(game, material));
            }
            return true;
        });


        woodButton.addListener(event -> {
            if(Gdx.input.justTouched()) {
                material = Material.WOOD;
                updateButtonStates();
            }
            return true;
        });
        plasticButton.addListener(event -> {

            if (Gdx.input.justTouched()) {
                if (!plasticBought && currentEPoints >= plasticPrice) {
                    plasticBought = true;
                    currentEPoints -= plasticPrice;
                    game.setPlasticBought(true);
                    material = Material.PLASTIC;
                } else if (plasticBought) {
                    material = Material.PLASTIC;
                }


            }
            updateButtonStates();
            return true;
        });
        ironButton.addListener(event -> {
            if (Gdx.input.justTouched()) {
                if (!ironBought && currentEPoints >= ironPrice) {
                    ironBought = true;
                    currentEPoints -= ironPrice;
                    game.setIronBought(true);
                    material = Material.IRON;
                }else if (ironBought) {
                    material = Material.IRON;
                }


            }
            updateButtonStates();
            return true;
        });

        goldButton.addListener(event -> {
            if (Gdx.input.justTouched()) {
                if (!goldBought && currentEPoints >= goldPrice) {
                    goldBought = true;
                    currentEPoints -= goldPrice;
                    game.setGoldBought(true);
                    material = Material.GOLD;
                }

                else if (goldBought) {
                    material = Material.GOLD;
                }
            }
            updateButtonStates();
            return true;
        });

        osmiumButton.addListener(event -> {
            if (Gdx.input.justTouched()) {
                if (!osmiumBought && currentEPoints >= osmiumPrice) {
                    osmiumBought = true;
                    currentEPoints -= osmiumPrice;
                    game.setOsmiumBought(true);
                    material = Material.OSMIUM;
                }

                else if (osmiumBought) {
                    material = Material.OSMIUM;
                }
            }
            updateButtonStates();
            return true;
        });


        table.add(buyGearButton).width(300).height(50).padBottom(20).row();
        table.add(woodButton).width(300).height(50).padBottom(20).row();
        table.add(plasticButton).width(300).height(50).padBottom(20).row();
        table.add(ironButton).width(300).height(50).padBottom(20).row();
        table.add(goldButton).width(300).height(50).padBottom(20).row();
        table.add(osmiumButton).width(300).height(50).padBottom(20).row();
        table.add(backButton).width(300).height(50);

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
            addGear();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(shopBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
        shopBackground.dispose();
    }
}
