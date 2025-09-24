package com.coruptical.cannonUtils.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {
    public static String cmsg(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String formatTickLocation(int tick, Location loc){
        String floc = String.format("X: %s Y: %s: Z: %s", loc.getX(), loc.getY(), loc.getZ());
        return ChatColor.translateAlternateColorCodes('&',
                String.format("&7[&fTick %s&7]: &b%s",
                        tick, floc));
    }
}
