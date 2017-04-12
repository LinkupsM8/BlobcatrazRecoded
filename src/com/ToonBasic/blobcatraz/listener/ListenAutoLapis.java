package com.ToonBasic.blobcatraz.listener;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenAutoLapis implements Listener {
	private static final ItemStack LAPIS = ItemUtil.getItem("lapis lazuli", 3, (short) 4);
	private static List<Inventory> list = Util.newList();
	
	@EventHandler
	public void open(InventoryOpenEvent e) {
		Inventory i = e.getInventory();
		if(i instanceof EnchantingInventory) {
			EnchantingInventory ei = (EnchantingInventory) i;
			ei.setItem(1, LAPIS);
			list.add(ei);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		Inventory i = e.getInventory();
		if(list.contains(i)) {
			i.setItem(1, null);
			list.remove(i);
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		if(list.contains(i)) {
			int slot = e.getSlot();
			if(slot == 1) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ench(EnchantItemEvent e) {
		Inventory i = e.getInventory();
		if(list.contains(i)) i.setItem(1, LAPIS);
	}
}