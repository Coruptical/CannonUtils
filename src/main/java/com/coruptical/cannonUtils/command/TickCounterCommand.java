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

public class TickCounterCommand implements CommandExecutor {
    private Material tickCounterMaterial = ConfigHandler.getInstance().getTickCounterMaterial();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission(ConfigHandler.getInstance().getTickCounterPermission())) {
            p.sendMessage(Utils.cmsg(Strings.NO_CMD_PERMISSION.label));
            return true;
        }

        ItemStack counterItem = new ItemStack(tickCounterMaterial);
        ItemMeta meta = counterItem.getItemMeta();

        meta.setItemName(Utils.cmsg(Strings.TICK_COUNTER_NAME.label));
        meta.getPersistentDataContainer().set(NSKey.TICK_COUNTER.key, PersistentDataType.BOOLEAN, true);
        counterItem.setItemMeta(meta);

        p.getInventory().addItem(counterItem);
        return true;
    }
}
