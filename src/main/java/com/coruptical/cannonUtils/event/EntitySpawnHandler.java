package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.CannonUtils;
import com.coruptical.cannonUtils.models.TrackedBlock;
import com.coruptical.cannonUtils.models.TrackedEntity;
import com.coruptical.cannonUtils.utils.DataHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashMap;

public class EntitySpawnHandler implements Listener {
    PlayerInfoHandler pif = PlayerInfoHandler.getInstance();
    CannonUtils plugin = CannonUtils.getInstance();
    // TODO: Implement entity tracker behaviour here

    // TODO: I need to split the tracked entities into TNT and FallingBlock otherwise im screwed
    @EventHandler
    public void onTrackedTNTSpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.TNT)){
            Location spawnLoc = entity.getLocation().getBlock().getLocation();
            for (Location loc : DataHandler.getInstance().getTrackedPrimedTNT().keySet()){
                if (!loc.equals(spawnLoc)) continue;
                TrackedBlock trackedBlock = DataHandler.getInstance().getTrackedPrimedTNT().get(loc);
                TrackedEntity trackedEntity = new TrackedEntity(entity, new HashMap<>());
                trackedBlock.setTrackedEntity(plugin, trackedEntity);
                DataHandler.getInstance().getTrackedPrimedTNT().remove(loc);
                return;
            }
        } else if (entity instanceof FallingBlock){
            Location spawnLoc = entity.getLocation().getBlock().getLocation();
            for (Player p : pif.getPlayerTrackedBlocks().keySet()){
                for (TrackedBlock trackedBlock : pif.getPlayerTrackedBlocks().get(p)){
                    if (!spawnLoc.equals(trackedBlock.getLoc())) continue;
                    TrackedEntity trackedEntity = new TrackedEntity(entity, new HashMap<>());
                    trackedBlock.setTrackedEntity(plugin, trackedEntity);
                    return;
                }
            }
        }
    }

    // This detects when a tracked dispenser spawns a piece of tnt so it can be tracked
    @EventHandler
    public void onTNTPrimed(TNTPrimeEvent e){
        if (e.getPrimingBlock() == null ) return;
        if (!e.getPrimingBlock().getType().equals(Material.DISPENSER)) return;

        Location primingBlockLocation = e.getPrimingBlock().getLocation();
        for (Player p : pif.getPlayerTrackedBlocks().keySet()){
            for (TrackedBlock trackedBlock : pif.getPlayerTrackedBlocks().get(p)){
                if (!primingBlockLocation.equals(trackedBlock.getLoc())) continue;
                DataHandler.getInstance().getTrackedPrimedTNT().put(trackedBlock.getLoc(), trackedBlock);
            }
        }
    }
}
