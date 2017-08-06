package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CommandDiscard extends PlayerCommand implements Listener {
	private static final String TITLE = Util.color("&8Trash Can");
	public CommandDiscard() {super("discard", "", "blobcatraz.player.discard", "trashcan", "trash", "delete", "recycle");}
	
	@Override
	public void run(Player p, String[] args) {
		Inventory i = Bukkit.createInventory(null, 54, TITLE);
		p.openInventory(i);
		String msg = prefix + "Opening a trash can...";
		p.sendMessage(msg);
	}
	
	@EventHandler
	public void trash(InventoryCloseEvent e) {
		Inventory i = e.getInventory();
		if(i != null) {
			String name = i.getName();
			if(name != null && name.equals(TITLE)) i.clear();
		}
	}
}