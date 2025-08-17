package com.coruptical.cannonUtils;

import com.coruptical.cannonUtils.command.FireCommand;
import com.coruptical.cannonUtils.command.MagicSandCommand;
import com.coruptical.cannonUtils.command.TickCounterCommand;
import com.coruptical.cannonUtils.event.BlockInteractHandler;
import com.coruptical.cannonUtils.event.BlockPlaceBreakHandler;
import com.coruptical.cannonUtils.event.ExplosionEventHandler;
import com.coruptical.cannonUtils.event.PlayerJoinQuitHandler;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.ProcessScheduler;
import org.bukkit.plugin.java.JavaPlugin;

public final class CannonUtils extends JavaPlugin {
    private static CannonUtils instance;

    public static CannonUtils getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigHandler.getInstance().init(this);
        loadListeners();
        loadCommands();

        ProcessScheduler.getInstance().init(this);
    }

    @Override
    public void onDisable() {

    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new ExplosionEventHandler(), instance);
        getServer().getPluginManager().registerEvents(new BlockInteractHandler(), instance);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitHandler(), instance);
        getServer().getPluginManager().registerEvents(new BlockPlaceBreakHandler(), instance);
    }

    private void loadCommands(){
        getCommand("tickcounter").setExecutor(new TickCounterCommand());
        getCommand("magicsand").setExecutor(new MagicSandCommand());
        getCommand("fire").setExecutor(new FireCommand());
    }
}
