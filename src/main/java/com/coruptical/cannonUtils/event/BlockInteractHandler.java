package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.models.Constants;
import com.coruptical.cannonUtils.models.NSKey;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.models.TickCounter;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractHandler implements Listener {
    private Material tickCounterMaterial = ConfigHandler.getInstance().getTickCounterMaterial();



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
                counter.addTicks(repeater.getDelay());
            }
            case COMPARATOR -> {
                counter.addTicks(2);
                counter.addPriority(1);
            }
            // TODO: I need to handle pistons firing, and detect when theres 2 in sequence to create 3 gt delay instead of 2.
        }

        counter.sendTiming(p);
    }

    // Handle interaction for saving /fire button
    @EventHandler
    public void onPlayerPressButton(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!e.hasBlock()) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!p.hasPermission(ConfigHandler.getInstance().getFirePermission())) return;

        Block b = e.getClickedBlock();
        // TODO: Make any button be saveable and keep its delay the same
        if (b.getType().equals(Material.STONE_BUTTON)){
            PlayerInfoHandler.getInstance().getPlayerButtons().replace(p, b);
            p.sendMessage(Utils.cmsg(Strings.BUTTON_SAVED.label));
        }
    }
}
