package com.coruptical.cannonUtils.models;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class TrackedEntity {
    private Entity entity;
    private HashMap<Integer, Location> tickLocations;

    public TrackedEntity(Entity entity, HashMap<Integer, Location> tickLocations) {
        this.entity = entity;
        this.tickLocations = tickLocations;
    }

    public Entity getEntity() {
        return entity;
    }

    public HashMap<Integer, Location> getTickLocations() {
        return tickLocations;
    }

    public void startTracking(Plugin plugin) {
        int tick = 0;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead() || entity.isOnGround()) {
                    cancel();
                    return;
                }
                tickLocations.put(tick, entity.getLocation());
                plugin.getServer().broadcastMessage(String.format("Tick %s: %s at %s", tick, entity.getType(), entity.getLocation()));
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }
}
