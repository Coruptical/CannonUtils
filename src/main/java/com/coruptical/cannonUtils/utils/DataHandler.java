package com.coruptical.cannonUtils.utils;

import com.coruptical.cannonUtils.models.TrackedBlock;
import org.bukkit.Location;

import java.util.HashMap;

public class DataHandler {
    private DataHandler(){

    }

    private static final DataHandler instance = new DataHandler();

    public static DataHandler getInstance() {
        return instance;
    }

    private HashMap<Location, TrackedBlock> trackedPrimedTNT = new HashMap<>();

    public HashMap<Location, TrackedBlock> getTrackedPrimedTNT() {
        return trackedPrimedTNT;
    }
}
