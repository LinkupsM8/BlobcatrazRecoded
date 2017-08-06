package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ListenAutoLapis implements Listener {
	private static final ItemStack LAPIS = ItemUtil.newItem(Material.INK_SACK, 3, 4);
	private static List<Inventory> LIST = Util.newList();
	
	@EventHandler
	public void open(InventoryOpenEvent e) {
		Inventory i = e.getInventory();
		if(i instanceof EnchantingInventory) {
			EnchantingInventory ei = (EnchantingInventory) i;
			ei.setSecondary(LAPIS);
			LIST.add(i);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		Inventory i = e.getInventory();
		if(LIST.contains(i) && i instanceof EnchantingInventory) {
			EnchantingInventory ei = (EnchantingInventory) i;
			ei.setSecondary(ItemUtil.AIR);
			LIST.remove(i);
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		if(LIST.contains(i) && i instanceof EnchantingInventory) {
			int slot = e.getRawSlot();
			if(slot == 1) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ench(EnchantItemEvent e) {
		Inventory i = e.getInventory();
		if(LIST.contains(i) && i instanceof EnchantingInventory) {
			EnchantingInventory ei = (EnchantingInventory) i;
			ei.setSecondary(LAPIS);
		}
	}
}