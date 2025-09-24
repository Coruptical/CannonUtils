package com.coruptical.cannonUtils.utils;

import com.coruptical.cannonUtils.CannonUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class ProcessScheduler {
    private ProcessScheduler(){

    }

    private static final ProcessScheduler instance = new ProcessScheduler();
    private CannonUtils plugin;

    public static ProcessScheduler getInstance(){
        return instance;
    }

    public void init(CannonUtils plugin){
        this.plugin = plugin;

        startMagicSand();
    }

    private void startMagicSand(){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Location loc : PlayerInfoHandler.getInstance().getMagicSandLocations().keySet()){
                    Material msType = PlayerInfoHandler.getInstance().getMagicSandLocations().get(loc).getMaterial();
                    loc.getBlock().getRelative(0, -1, 0).setType(msType);
                }
            }
        };
        runnable.runTaskTimer(this.plugin, 0L, ConfigHandler.getInstance().getMagicSandPeriod());
    }
}
