package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.utility.PlayerUtil;

public class ListenMine implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
        Block b = e.getBlock();
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();
        
        for(ItemStack is : b.getDrops()) {
        	int slot = pi.firstEmpty();
        	if(slot == -1) {
        		String msg = "Your inventory is full!";
        		PlayerUtil.action(p, msg);
        		return;
        	} else {
        		pi.addItem(is);
        	}
        }
        
        b.setType(Material.AIR);
        b.getDrops().clear();
    }
}
