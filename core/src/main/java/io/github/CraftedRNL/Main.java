package io.github.CraftedRNL;

//import Game class
import com.badlogic.gdx.Game;

// main class that extends the game class, hierarchy stuff
public class Main extends Game {
    //method called when game starts
    @Override
    public void create() {
        //sets initial screen to the menScreen
        setScreen(new MenuScreen(this));
    }
    //Players energy points, starts a 0
    private int ePoints = 0;

    //getter method to get the epoints
    public int getEPoints() { return ePoints; }
    //setter method to set epoints
    public void setEPoints(int points) { this.ePoints = points; }

    //Players starting gearAmount
    private int gearCount = 0;
    //getter method to get gearcount
    public int getGearCount(){
        return gearCount;
    }
    //setter method to set gearCount
    public void setGearCount(int gearCount){
        this.gearCount = gearCount;
    }

    // variables to track which materials have been unlocked
    private boolean plasticBought = false;
    private boolean ironBought = false;
    private boolean goldBought = false;
    private boolean osmiumBought = false;

    // getter method to check if the mat is bought
    public boolean isPlasticBought() { return plasticBought; }
    public boolean isIronBought() { return ironBought; }
    public boolean isGoldBought() { return goldBought; }
    public boolean isOsmiumBought() { return osmiumBought; }

    //setter method to update the status of the mat
    public void setPlasticBought(boolean bought) {plasticBought = bought;}
    public void setIronBought(boolean bought) {ironBought = bought;}
    public void setGoldBought(boolean bought) {goldBought = bought;}
    public void setOsmiumBought(boolean bought) {osmiumBought = bought;}

    // Current Material defaults to wood
    //Material is an enum with dif material types
    private Material material = Material.WOOD;
    // Getter for current material
    public Material getMaterial() { return material; }
    // Setter to change current material
    public void setMaterial(Material material) { this.material = material; }
}
