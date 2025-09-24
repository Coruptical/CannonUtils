package com.coruptical.cannonUtils.models;

import org.bukkit.NamespacedKey;

public enum NSKey {
    TICK_COUNTER("tick_counter"),
    MAGIC_SAND("magic_sand"),
    ENTITY_TRACKER("entity_tracker"),
    STACK_CLEARER("stack_clearer");

    public final NamespacedKey key;

    NSKey(String keyName){
        this.key = new NamespacedKey("cannonutils", keyName);
    }
}
