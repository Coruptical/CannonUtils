package com.coruptical.cannonUtils.models;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class TrackedBlock {
    private int id;
    private TrackedEntity trackedEntity;
    private Location loc;

    public TrackedBlock(int id, Location loc) {
        this.id = id;
        this.trackedEntity = null;
        this.loc = loc;
    }

    public int getId() {
        return id;
    }

    public TrackedEntity getTrackedEntity() {
        return trackedEntity;
    }

    public Location getLoc() {
        return loc;
    }

    public void setTrackedEntity(Plugin plugin, TrackedEntity trackedEntity){
        this.trackedEntity = trackedEntity;
        trackedEntity.startTracking(plugin);
    }
}
