package io.github.CraftedRNL;
//this is the enum with diff types of mats in the game

public enum Material{
    //enum with their properties
    //density and the name
    WOOD(700f, "Wood"),
    PLASTIC(1050f, "Plastic"),
    IRON(7870F, "Iron"),
    GOLD(19320f, "Gold"),
    OSMIUM(22590f, "Osmium");
    // variables each mat will have
    final float density;
    final String name;

    //the constructor, auto called
    Material(float density, String name) {
        this.density = density;
        this.name = name;
    }
}
