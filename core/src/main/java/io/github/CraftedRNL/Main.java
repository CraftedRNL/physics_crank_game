package io.github.CraftedRNL;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
    private int ePoints = 0;

    public int getEPoints() { return ePoints; }
    public void setEPoints(int points) { this.ePoints = points; }

    private int gearCount = 0;
    public int getGearCount(){
        return gearCount;
    }
    public void setGearCount(int gearCount){
        this.gearCount = gearCount;
    }
    private boolean plasticBought = false;
    private boolean ironBought = false;
    private boolean goldBought = false;
    private boolean osmiumBought = false;

    public boolean isPlasticBought() { return plasticBought; }
    public boolean isIronBought() { return ironBought; }
    public boolean isGoldBought() { return goldBought; }
    public boolean isOsmiumBought() { return osmiumBought; }
    public void setPlasticBought(boolean bought) {plasticBought = bought;}
    public void setIronBought(boolean bought) {ironBought = bought;}
    public void setGoldBought(boolean bought) {goldBought = bought;}
    public void setOsmiumBought(boolean bought) {osmiumBought = bought;}

    private Material material = Material.WOOD;
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
}
