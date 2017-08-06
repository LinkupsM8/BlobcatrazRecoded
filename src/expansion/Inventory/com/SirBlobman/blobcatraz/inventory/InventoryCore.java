package com.SirBlobman.blobcatraz.inventory;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class InventoryCore extends Core implements Listener {
	public static InventoryCore instance;
	
	@Override
	public void onEnable() {
		instance = this;
		Util.regEvents((Plugin) this, this);
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) saveContents(p, p.getWorld());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void world(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		World from = e.getFrom();
		World to = p.getWorld();
		if(!from.equals(to)) {
			saveContents(p, from);
			
			List<ItemStack> toList = ConfigDatabase.getInventory(to, p);
			ItemStack[] toconts = toList.toArray(new ItemStack[0]);
			pi.clear();
			pi.setContents(toconts);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		World w = p.getWorld();
		List<ItemStack> list = ConfigDatabase.getInventory(w, p);
		ItemStack[] contents = list.toArray(new ItemStack[0]);
		pi.clear();
		pi.setContents(contents);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		World w = p.getWorld();
		saveContents(p, w);
	}
	
	private void saveContents(Player p, World w) {
		PlayerInventory pi = p.getInventory();
		ItemStack[] contents = pi.getContents();
		List<ItemStack> list = Util.newList(contents);
		ConfigDatabase.setInventory(w, p, list);
		pi.clear();
	}
}