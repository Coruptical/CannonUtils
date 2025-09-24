package com.coruptical.cannonUtils.utils;

import com.coruptical.cannonUtils.CannonUtils;
import org.bukkit.Material;

import java.util.logging.Level;

public class ConfigHandler {
    private Material protectionBlock = Material.EMERALD_BLOCK; // Default
    private Material tickCounterMaterial = Material.BLAZE_ROD; // Default
    private Material entityTrackerMaterial = Material.GOLDEN_APPLE; // Default
    private Material stackClearerMaterial = Material.BONE; // DEFAULT
    private CannonUtils plugin;

    private ConfigHandler(){

    }

    private static final ConfigHandler instance = new ConfigHandler();

    public static ConfigHandler getInstance(){
        return instance;
    }

    public void init(CannonUtils plugin){
        this.plugin = plugin;

        // Protection block
        String configMaterial = plugin.getConfig().getString("protection_block");
        Material mat = Material.matchMaterial(configMaterial);
        if (mat != null){
            this.protectionBlock = mat;
            plugin.getLogger().log(Level.INFO, String.format("Protection block set to %s", mat));
        }

        // Tick counter
        configMaterial = plugin.getConfig().getString("tick_counter");
        mat = Material.matchMaterial(configMaterial);
        if (mat != null){
            this.tickCounterMaterial = mat;
            plugin.getLogger().log(Level.INFO, String.format("Tick counter material set to %s", mat));
        }

        // Entity tracker
        configMaterial = plugin.getConfig().getString("entity_tracker");
        mat = Material.matchMaterial(configMaterial);
        if (mat != null){
            this.entityTrackerMaterial = mat;
            plugin.getLogger().log(Level.INFO, String.format("Entity tracker material set to %s", mat));
        }

        // Stack clearer
        configMaterial = plugin.getConfig().getString("stack_clearer");
        mat = Material.matchMaterial(configMaterial);
        if (mat != null){
            this.stackClearerMaterial = mat;
            plugin.getLogger().log(Level.INFO, String.format("Stack clearer material set to %s", mat));
        }

    }

    // Getters

    public Material getProtectionBlock() {
        return protectionBlock;
    }

    public Material getTickCounterMaterial(){
        return tickCounterMaterial;
    }

    public Material getStackClearerMaterial() {
        return stackClearerMaterial;
    }

    public String getTickCounterPermission(){
        return plugin.getConfig().getString("tick_counter_permission");
    }

    public String getMagicSandPermission(){
        return plugin.getConfig().getString("magic_sand_permission");
    }

    public String getFirePermission(){
        return plugin.getConfig().getString("fire_permission");
    }

    public String getEntityTrackerPermission() {
        return plugin.getConfig().getString("entity_tracker_permission");
    }

    public String getStackClearerPermission() {
        return plugin.getConfig().getString("stack_clearer_permission");
    }

    public String getMsClearPermission() {
        return plugin.getConfig().getString("msclear_permission");
    }

    public String getTntFillPermission() {
        return plugin.getConfig().getString("tntfill_permission");
    }

    public long getMagicSandPeriod(){
        long period = 8L; // Default
        long configPeriod = plugin.getConfig().getLong("magic_sand_period");
        if (configPeriod <= 0) return period;
        period = configPeriod;
        return period;
    }

    public int getMaxTntFillRadius(){
        int max = 50; // Default
        int configMax = plugin.getConfig().getInt("tntfill_max_radius");
        if (configMax <= 0) return max;
        max = configMax;
        return max;
    }

    public Material getEntitytrackerMaterial() {
        return entityTrackerMaterial;
    }

}
