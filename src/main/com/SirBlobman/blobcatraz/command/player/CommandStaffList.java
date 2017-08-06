package com.SirBlobman.blobcatraz.command.player;

import static com.SirBlobman.blobcatraz.command.staff.CommandVanish.vanished;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandStaffList extends PlayerCommand implements Listener {
	private static final String TITLE = Util.color("&2Online Staff");
	public CommandStaffList() {super("stafflist", "", "blobcatraz.player.stafflist", "listadmins", "admins", "onlinestaff");}
	
	@Override
	public void run(Player p, String[] args) {
		String msg = "Opening the staff GUI...";
		PlayerUtil.action(p, msg);
		gui(p);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		if(i != null) {
			String name = i.getName();
			if(name != null && name.equals(TITLE)) e.setCancelled(true);
		}
	}
	
	public static void gui(Player p) {
		Inventory i = Bukkit.createInventory(null, 9, TITLE);
		
		int j = 0;
		String perm = "blobcatraz.staff";
		for(Player o : Bukkit.getOnlinePlayers()) {
			if(o.hasPermission(perm)) {
				if(!vanished.contains(o)) {
					String name = o.getName();
					String disp = Util.format("&5%1s", name);
					ItemStack is = ItemUtil.newHead(name, disp);
					i.setItem(j, is);
					j++;
				}
			}
		}
		
		p.openInventory(i);
	}
}