package com.coruptical.cannonUtils.event;

import com.coruptical.cannonUtils.utils.ConfigHandler;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;

public class ExplosionEventHandler implements Listener {
    private Material protectionBlock = ConfigHandler.getInstance().getProtectionBlock();
    private int MAX_Y = 319; // TODO: Make these configurable through config?
    private int MIN_Y = -64;

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        World world = e.getEntity().getWorld();
        if (e.getEntity().getType().equals(EntityType.TNT)){
            Iterator<Block> blockIterator = e.blockList().iterator();
            removeProtectedBlocks(world, blockIterator);
        }
    }

    private void removeProtectedBlocks(World world, Iterator<Block> iterator){
        while (iterator.hasNext()){
            Block b = iterator.next();
            int x = b.getX();
            int z = b.getZ();
            for (int y = MIN_Y; y <= MAX_Y; y++){
                Block testBlock = world.getBlockAt(x, y, z);
                if (testBlock.getType().equals(protectionBlock)){
                    iterator.remove();
                    break;
                }
            }
        }
    }

}
