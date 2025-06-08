package io.github.CraftedRNL;

public enum Material{
    WOOD(700f, "Wood"),
    PLASTIC(1050f, "Plastic"),
    IRON(7870F, "Iron"),
    GOLD(19320f, "Gold"),
    OSMIUM(22590f, "Osmium");

    final float density;
    final String name;

    Material(float density, String name) {
        this.density = density;
        this.name = name;
    }
}
