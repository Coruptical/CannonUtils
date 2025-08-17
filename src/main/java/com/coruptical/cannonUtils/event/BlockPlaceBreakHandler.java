package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.models.Constants;
import com.coruptical.cannonUtils.models.MagicSand;
import com.coruptical.cannonUtils.models.NSKey;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlaceBreakHandler implements Listener {
    PlayerInfoHandler handler = PlayerInfoHandler.getInstance();

    // Handle place events
    @EventHandler
    public void onPlaceMagicSand(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (!Constants.MS_ACCEPTED.contains(b.getType())) return;
        if (!p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NSKey.MAGIC_SAND.key)) return;

        Material msType = b.getType();
        b.setType(Material.SEA_LANTERN);
        // e.setCancelled(true);

        MagicSand ms = new MagicSand(b.getLocation(), msType);
        handler.getPlayerMagicSand().get(p).add(ms);
        handler.getMagicSandLocations().put(b.getLocation(), ms);
        p.sendMessage(Utils.cmsg(Strings.MAGIC_SAND_PLACED.label));
    }

    // Handle break events
    @EventHandler
    public void onBreakMagicSand(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (!b.getType().equals(Material.SEA_LANTERN)) return;
        Location bLoc = b.getLocation();
        if (!handler.getMagicSandLocations().containsKey((bLoc))) return;

        MagicSand ms = handler.getMagicSandLocations().get(bLoc);
        handler.getPlayerMagicSand().get(p).remove(ms);
        handler.getMagicSandLocations().remove(bLoc);
    }
}
