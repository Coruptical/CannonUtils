package com.coruptical.cannonUtils;

import org.bukkit.plugin.java.JavaPlugin;

public final class CannonUtils extends JavaPlugin {
    private static CannonUtils instance;

    public static CannonUtils getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {

    }
}
