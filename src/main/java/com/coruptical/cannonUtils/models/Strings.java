package com.coruptical.cannonUtils.models;

public enum Strings {
    TICK_COUNTER_NAME("&7[&6Tick Counter&7]"),
    TICK_COUNTER_CLEAR("&7Tick counter cleared."),
    NO_CMD_PERMISSION("&cYou don't have permission to use this command."),
    MAGIC_SAND_NAME("&7[&bMagic Sand&7]"),
    INVALID_MAGIC_SAND_MATERIAL("&7Invalid magic sand material."),
    MAGIC_SAND_PLACED("&aMagic sand placed."),
    BUTTON_SAVED("&7Button saved for /fire.");

    public final String label;

    Strings(String label) {
        this.label = label;
    }
}
