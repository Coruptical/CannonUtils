package com.coruptical.cannonUtils;

import com.coruptical.cannonUtils.event.BlockInteractHandler;
import com.coruptical.cannonUtils.event.ExplosionEventHandler;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;


public final class CannonUtils extends JavaPlugin {
    private static CannonUtils instance;

    public static CannonUtils getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.getInstance().init(this);
        loadListeners();

    }

    @Override
    public void onDisable() {

    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new ExplosionEventHandler(), this);
        getServer().getPluginManager().registerEvents(new BlockInteractHandler(), this);
    }
}
