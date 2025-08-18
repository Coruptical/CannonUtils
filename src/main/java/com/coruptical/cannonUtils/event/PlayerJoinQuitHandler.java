package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.models.MagicSand;
import com.coruptical.cannonUtils.models.TickCounter;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;

public class PlayerJoinQuitHandler implements Listener {
    PlayerInfoHandler handler = PlayerInfoHandler.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        handler.getPlayerTickCounters().put(p, new TickCounter());
        handler.getPlayerMagicSand().put(p, new HashSet<>());
        handler.getPlayerButtons().put(p, null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        handler.getPlayerTickCounters().remove(p);

        // Remove magic sand
        HashSet<MagicSand> playerSands = handler.getPlayerMagicSand().get(p);
        for (MagicSand ms : playerSands){
            handler.getMagicSandLocations().remove(ms.getLoc());
        }
        handler.getPlayerMagicSand().remove(p);
        handler.getPlayerButtons().remove(p);
    }

}
