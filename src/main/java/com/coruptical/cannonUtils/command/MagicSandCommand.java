package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.models.Constants;
import com.coruptical.cannonUtils.models.NSKey;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.PlayerInfoHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class MagicSandCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getMagicSandPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        // TODO: Implement a list of falling blocks: sand, red sand, gravel, anvils, (scaffolding??)
        if (args.length < 1){
            p.sendMessage(Utils.cmsg(Strings.TOO_FEW_ARGS.label));
            return true;
        }

        String arg1 =  args[0].toUpperCase();
        Material mat = null;
        mat = Material.matchMaterial(arg1);

        if (mat == null) {
            p.sendMessage(Utils.cmsg(Strings.INVALID_MAGIC_SAND_MATERIAL.label));
            return true;
        }

        if (!Constants.MS_ACCEPTED.contains(mat)) {
            p.sendMessage(Utils.cmsg(Strings.INVALID_MAGIC_SAND_MATERIAL.label));
            return true;
        }

        ItemStack msItem = new ItemStack(mat);
        ItemMeta meta = msItem.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setItemName(Utils.cmsg(Strings.MAGIC_SAND_NAME.label));
        meta.getPersistentDataContainer().set(NSKey.MAGIC_SAND.key, PersistentDataType.BOOLEAN, true);
        msItem.setItemMeta(meta);

        p.getInventory().addItem(msItem);
        return true;
    }
}
