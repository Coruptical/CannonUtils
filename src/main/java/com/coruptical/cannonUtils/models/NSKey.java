package com.coruptical.cannonUtils.models;

import org.bukkit.NamespacedKey;

public enum NSKey {
    TICK_COUNTER("tick_counter"),
    MAGIC_SAND("magic_sand");

    public final NamespacedKey key;

    NSKey(String keyName){
        this.key = new NamespacedKey("cannonutils", keyName);
    }
}
