package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.models.MagicSand;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class MSClearCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getMsClearPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        HashSet <MagicSand> magicSands = PlayerInfoHandler.getInstance().getPlayerMagicSand().get(p);
        for (MagicSand sand : magicSands) {
            sand.getLocation().getBlock().setType(Material.SANDSTONE);
            PlayerInfoHandler.getInstance().getMagicSandLocations().remove(sand.getLocation());
        }
        int numSands = magicSands.size();
        PlayerInfoHandler.getInstance().getPlayerMagicSand().remove(p);
        p.sendMessage(Utils.cmsg(String.format("&aCleared &f%s&a magic sand.", numSands)));
        return true;
    }
}
