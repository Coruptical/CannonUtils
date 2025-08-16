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
        ConfigHandler.getInstance().init(this);
        loadListeners();
        loadCommands();

        ProcessScheduler.getInstance().init(this);
    }

    @Override
    public void onDisable() {

    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new ExplosionEventHandler(), this);
        getServer().getPluginManager().registerEvents(new BlockInteractHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitHandler(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceBreakHandler(), this);
    }

    private void loadCommands(){
        getCommand("tickcounter").setExecutor(new TickCounterCommand());
        getCommand("magicsand").setExecutor(new MagicSandCommand());
        getCommand("fire").setExecutor(new FireCommand());
    }
}
