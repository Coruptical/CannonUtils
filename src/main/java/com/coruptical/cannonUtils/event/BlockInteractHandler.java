package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.models.*;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class BlockInteractHandler implements Listener {
    private Material tickCounterMaterial = ConfigHandler.getInstance().getTickCounterMaterial();
    private Material entityTrackerMaterial = ConfigHandler.getInstance().getEntitytrackerMaterial();
    private Material stackClearerMaterial = ConfigHandler.getInstance().getStackClearerMaterial();

    // TODO: List of things to check: repeater, comparator, piston, observer,

    // Handle tick counter interaction
    @EventHandler
    public void onPlayerUseTickCounter(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasItem()) return;
        if (!e.hasBlock()) return;
        if (!e.getItem().getType().equals(tickCounterMaterial)) return;
        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(NSKey.TICK_COUNTER.key)) return;

        Block b = e.getClickedBlock();
        if (!Constants.TC_ACCEPTED.contains(b.getType())) return;
        e.setCancelled(true);

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        TickCounter counter = PlayerInfoHandler.getInstance().getPlayerTickCounters().get(p);

        switch (b.getType()){
            case REPEATER -> {
                Repeater repeater = (Repeater) b.getState().getBlockData();
                counter.addTicks(repeater.getDelay() * 2);
            }
            case COMPARATOR -> {
                counter.addTicks(2);
                counter.addPriority(1);
            }
            case OBSERVER -> counter.addTicks(2);
            case REDSTONE_TORCH -> counter.addTicks(1);
            // TODO: I need to handle pistons firing, and detect when theres 2 in sequence to create 3 gt delay instead of 2.
        }

        counter.sendTiming(p);
    }

    // Handle interaction for clearing tick counter
    @EventHandler
    public void onPlayerClearTickCounter(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasItem()) return;
        if (!e.getItem().getType().equals(tickCounterMaterial)) return;
        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(NSKey.TICK_COUNTER.key)) return;
        if (!(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) return;

        e.setCancelled(true);
        TickCounter counter = PlayerInfoHandler.getInstance().getPlayerTickCounters().get(p);
        counter.clear(p);
    }

    // Handle interaction for saving /fire button
    @EventHandler
    public void onPlayerPressButton(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasBlock()) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block b = e.getClickedBlock();
        if (!b.getType().equals(Material.STONE_BUTTON)) return;
        if (!p.hasPermission(ConfigHandler.getInstance().getFirePermission())) return;

        // TODO: Make any button be saveable and store the right delay
        PlayerInfoHandler.getInstance().getPlayerButtons().replace(p, b);
        p.sendMessage(Utils.cmsg(Strings.BUTTON_SAVED.label));
    }

    // Handle interaction for entity tracker
    // TODO:  I need to make this "tracked block" more precise, as this will probably be different for sand and tnt.
    @EventHandler
    public void onPlayerUseEntityTracker(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasItem()) return;
        if (!e.hasBlock()) return;
        if (!e.getItem().getType().equals(entityTrackerMaterial)) return;
        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(NSKey.ENTITY_TRACKER.key)) return;
        e.setCancelled(true);

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block b = e.getClickedBlock();
        if (!Constants.ET_ACCEPTED.contains(b.getType())) return;

        int id = PlayerInfoHandler.getInstance().getPlayerLowestTrackingIDs().get(p);

        TrackedBlock trackedBlock = new TrackedBlock(id, b.getLocation());
        PlayerInfoHandler.getInstance().getPlayerLowestTrackingIDs().replace(p, id+1);
        PlayerInfoHandler.getInstance().getPlayerTrackedBlocks().get(p).add(trackedBlock);
        p.sendMessage(Utils.cmsg(String.format("%s: &aAdded &f%s&a at &7%s",  Strings.ENTITY_TRACKER_NAME.label, b.getType(), b.getLocation())));
    }
    
    // Handle stack clearer interaction
    @EventHandler
    public void onPlayerUseStackClearer(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasItem()) return;
        if (!e.hasBlock()) return;
        if (!e.getItem().getType().equals(stackClearerMaterial)) return;
        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(NSKey.STACK_CLEARER.key)) return;
        e.setCancelled(true);

        if (!(e.getAction().equals(Action.LEFT_CLICK_AIR) ||
                e.getAction().equals(Action.LEFT_CLICK_BLOCK))) return;

        // TODO: find out how to get actual targeted block
        Block b = e.getPlayer().getTargetBlockExact(0);
        if (b == null) return;
        if (!Constants.MS_ACCEPTED.contains(b.getType())) return;
        clearStack(b);
    }

    private void clearStack(Block b){
        Block upBlock = b.getRelative(BlockFace.UP);
        Block downBlock = b.getRelative(BlockFace.DOWN);
        // Iterate up
        if (upBlock.getType().equals(b.getType())){
            clearStack(upBlock);
        }

        // Iterate down
        if (downBlock.getType().equals(b.getType())){
            clearStack(downBlock);
        }

        b.setType(Material.AIR);
    }
}
