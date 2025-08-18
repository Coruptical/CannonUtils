package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.CannonUtils;
import com.coruptical.cannonUtils.event.BlockInteractHandler;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FireCommand implements CommandExecutor {
    CannonUtils plugin = CannonUtils.getInstance();

    // TODO: Power the attached block in a good way, and add wood buttons too (diff timings)
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getFirePermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        if (PlayerInfoHandler.getInstance().getPlayerButtons().get(p) == null) {
            p.sendMessage(Utils.cmsg(Strings.BUTTON_NO_EXIST.label));
            return true;
        }

        Block b = PlayerInfoHandler.getInstance().getPlayerButtons().get(p);
        if (!b.getType().equals(Material.STONE_BUTTON)){
            p.sendMessage(Utils.cmsg(Strings.BUTTON_NO_EXIST.label));
            return true;
        }

        Switch button = (Switch) b.getBlockData();
        button.setPowered(true);
        b.setBlockData(button, true);

        new BukkitRunnable(){
            @Override
            public void run() {
                button.setPowered(false);
                b.setBlockData(button, true);
            }
        }.runTaskLater(plugin, 20L);
        p.sendMessage(Utils.cmsg(Strings.BUTTON_FIRED.label));
        return true;
    }
}
