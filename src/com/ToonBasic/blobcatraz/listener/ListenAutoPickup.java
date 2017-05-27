package com.ToonBasic.blobcatraz.listener;

import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;

public class ListenAutoPickup implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
    	if(e.isCancelled()) return;
    	Player p = e.getPlayer();
    	GameMode gm = p.getGameMode();
    	if(gm == GameMode.CREATIVE) return;
    	

        e.setCancelled(true);
    	Block b = e.getBlock();
    	PlayerInventory pi = p.getInventory();
        ItemStack held = pi.getItemInMainHand();
        for(ItemStack is : b.getDrops(held)) {
    		is = fortune(held, is);
        	Map<Integer, ItemStack> leftover = pi.addItem(is);
        	if(!leftover.isEmpty()) {
        		String msg = "Your inventory is full!";
        		PlayerUtil.action(p, msg);
        		PlayerUtil.ping(p);
        		for(ItemStack drop : leftover.values()) {
        			World w = p.getWorld();
        			Location l = b.getLocation();
        			w.dropItem(l, drop);
        		}
        	}
        }
        
        b.setType(Material.AIR);
        b.getDrops().clear();
    }
    
    private ItemStack fortune(ItemStack held, ItemStack give) {
    	int amount = give.getAmount();
    	ItemMeta meta = held.getItemMeta();
    	if(meta.hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
    		int lvl = meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
    		if(lvl > 0) {
    			Random rand = NumberUtil.random();
    			amount += (lvl + rand.nextInt(5));
    			give.setAmount(amount);
    			return give;
    		} else return give;
    	} else return give;
    }
}