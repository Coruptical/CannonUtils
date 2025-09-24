package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class TntFillCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getTntFillPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        int max_radius = ConfigHandler.getInstance().getMaxTntFillRadius();
        Location pLoc = p.getLocation();
        World world = p.getWorld();
        int px = pLoc.getBlockX();
        int py = pLoc.getBlockY();
        int pz = pLoc.getBlockZ();

        int numFilled = 0;

        for (int x = (px-max_radius); x <= (px+max_radius); x++){
            for (int z = (pz-max_radius); z <= (pz+max_radius); z++){
                for (int y = (py-max_radius); y <= (py+max_radius); y++){
                    Block b = world.getBlockAt(x, y, z);
                    if (b.getType() != Material.DISPENSER) continue;

                    Dispenser dispenser = (Dispenser) b.getBlockData();
                    ItemStack[] items = new ItemStack[9];
                    for (int i = 0; i < 9; i++){
                        items[i] = new ItemStack(Material.TNT, 64);
                    }
                    dispenser.getInventory().addItem(items);
                    numFilled++;
                }
            }
        }

        p.sendMessage(Utils.cmsg(String.format("&aFilled &f%s&a dispensers.", numFilled)));
        return true;
    }
}
