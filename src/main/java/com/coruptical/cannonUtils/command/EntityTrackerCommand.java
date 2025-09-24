package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.models.NSKey;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.models.TrackedBlock;
import com.coruptical.cannonUtils.models.TrackedEntity;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EntityTrackerCommand implements CommandExecutor {
    private Material entityTrackerMaterial = ConfigHandler.getInstance().getEntitytrackerMaterial();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getEntityTrackerPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        if (args.length == 0){
            p.sendMessage(Utils.cmsg(Strings.TOO_FEW_ARGS.label));
            return true;
        }

        // Getitem - gives player the entity tracker
        if (args[0].equalsIgnoreCase("getitem")){
            ItemStack trackerItem = new ItemStack(entityTrackerMaterial);
            ItemMeta meta = trackerItem.getItemMeta();

            meta.setItemName(Utils.cmsg(Strings.ENTITY_TRACKER_NAME.label));
            meta.getPersistentDataContainer().set(NSKey.ENTITY_TRACKER.key, PersistentDataType.BOOLEAN, true);
            trackerItem.setItemMeta(meta);

            p.getInventory().addItem(trackerItem);
        }

        // List - lists all the ids of the tracked entities and their types
        else if (args[0].equalsIgnoreCase("list")){
            // TODO: test this and make sure it works

            HashSet<TrackedBlock> trackedBlocks = PlayerInfoHandler.getInstance().getPlayerTrackedBlocks().get(p);
            handleListTracked(p, trackedBlocks);
        }

        // Get - retrieve the ticks and positions of a specific tracked entity via its ID.
        else if (args[0].equalsIgnoreCase("get")){
            // TODO: test this and make sure it works

            int argID;
            try {
                argID = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                p.sendMessage(Utils.cmsg(Strings.ERROR_INVALID_ID.label));
                return true;
            }

            HashSet<TrackedBlock> trackedBlocks = PlayerInfoHandler.getInstance().getPlayerTrackedBlocks().get(p);
            for (TrackedBlock block : trackedBlocks){
                if (block.getId() == argID){
                    handleGetTracked(p, block);
                    return true;
                }
            }
        }

        return true;
    }

    private void handleGetTracked(Player p, TrackedBlock b){
        TrackedEntity trackedEntity = b.getTrackedEntity();
        HashMap<Integer, Location> tickLocations = trackedEntity.getTickLocations();
        int size = tickLocations.size();
        for (int tick = 0; tick < size; tick++){
            Location loc = tickLocations.get(tick);
            p.sendMessage(Utils.formatTickLocation(tick, loc));
        }
    }

    private void handleListTracked(Player p, HashSet<TrackedBlock> trackedBlocks){
        int size = trackedBlocks.size();
        HashMap<Integer, TrackedBlock> blockMap = new HashMap<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (TrackedBlock trackedBlock : trackedBlocks){
            blockMap.put(trackedBlock.getId(), trackedBlock);
            ids.add(trackedBlock.getId());
        }

        ids.sort((o1, o2) -> 0);

        for (int id : ids){
            TrackedBlock block = blockMap.get(id);
            Location loc = block.getLoc();
            String floc = String.format("X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            p.sendMessage(Utils.cmsg(String.format("&7[&fID: %s&7]: %s", id, floc)));
        }
    }
}
