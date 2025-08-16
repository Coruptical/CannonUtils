package com.coruptical.cannonUtils.models;

import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.entity.Player;

public class TickCounter {
    private final String FORMAT = "&7GT: &a%.2f  &7RT: &a%.2f &7PR: &a%s";

    private float ticks;
    private int priority;

    public TickCounter() {
        this.ticks = 0;
        this.priority = 0;
    }

    public void addTicks(float amount){
        this.ticks += amount;
    }

    public void addPriority(int amount){
        this.priority += amount;
    }

    public void sendTiming(Player p){
        p.sendMessage(Utils.cmsg(String.format(FORMAT, ticks, ticks/2, priority)));
    }

    public void clear(Player p){
        this.ticks = 0;
        this.priority = 0;
        p.sendMessage(Utils.cmsg(Strings.TICK_COUNTER_CLEAR.label));
    }

    // Getters and setters

    public float getTicks() {
        return ticks;
    }

    public void setTicks(float ticks) {
        this.ticks = ticks;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
