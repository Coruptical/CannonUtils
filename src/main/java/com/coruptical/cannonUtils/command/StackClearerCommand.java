package com.coruptical.cannonUtils.command;

import com.coruptical.cannonUtils.models.NSKey;
import com.coruptical.cannonUtils.models.Strings;
import com.coruptical.cannonUtils.utils.ConfigHandler;
import com.coruptical.cannonUtils.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class StackClearerCommand implements CommandExecutor {
    private Material stackClearerMaterial = ConfigHandler.getInstance().getStackClearerMaterial();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getStackClearerPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        ItemStack clearerItem = new ItemStack(stackClearerMaterial);
        ItemMeta meta = clearerItem.getItemMeta();

        meta.setItemName(Utils.cmsg(Strings.STACK_CLEARER_NAME.label));
        meta.getPersistentDataContainer().set(NSKey.STACK_CLEARER.key, PersistentDataType.BOOLEAN, true);
        clearerItem.setItemMeta(meta);

        p.getInventory().addItem(clearerItem);

        return true;
    }
}
