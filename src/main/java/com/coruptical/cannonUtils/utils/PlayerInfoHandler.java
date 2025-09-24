package com.coruptical.cannonUtils.utils;

import com.coruptical.cannonUtils.models.MagicSand;
import com.coruptical.cannonUtils.models.TickCounter;
import com.coruptical.cannonUtils.models.TrackedBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerInfoHandler {


    // Instance setup
    private PlayerInfoHandler(){

    }

    private static final PlayerInfoHandler instance = new PlayerInfoHandler();

    public static PlayerInfoHandler getInstance(){
        return instance;
    }

    // Player setup

    private final HashMap<Player, TickCounter> playerTickCounters = new HashMap<>();
    private final HashMap<Player, HashSet<MagicSand>> playerMagicSand = new HashMap<>();
    private final HashMap<Location, MagicSand> magicSandLocations = new HashMap<>();
    private final HashMap<Player, Block> playerButtons = new HashMap<>();
    private final HashMap<Player, HashSet<TrackedBlock>> playerTrackedBlocks = new HashMap<>();
    private final HashMap<Player, Integer> playerLowestTrackingIDs = new HashMap<>();

    // Getters

    public HashMap<Player, Integer> getPlayerLowestTrackingIDs() {
        return playerLowestTrackingIDs;
    }

    public HashMap<Player, TickCounter> getPlayerTickCounters() {
        return playerTickCounters;
    }

    public HashMap<Player, HashSet<MagicSand>> getPlayerMagicSand() {
        return playerMagicSand;
    }

    public HashMap<Location, MagicSand> getMagicSandLocations() {
        return magicSandLocations;
    }

    public HashMap<Player, Block> getPlayerButtons() {
        return playerButtons;
    }

    public HashMap<Player, HashSet<TrackedBlock>> getPlayerTrackedBlocks() {
        return playerTrackedBlocks;
    }
}
