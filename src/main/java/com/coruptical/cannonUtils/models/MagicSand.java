package com.coruptical.cannonUtils.models;

import org.bukkit.Location;
import org.bukkit.Material;

public class MagicSand {
    private Location loc;
    private Material material;

    public MagicSand(Location loc, Material material) {
        this.loc = loc;
        this.material = material;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
