package com.SirBlobman.blobcatraz.command.player;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandKit extends PlayerCommand {
	public CommandKit() {super("kit", "[name]", "blobcatraz.player.kit");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length == 0) list(p);
		else {
			String name = Util.finalArgs(0, args);
			if(ConfigKits.exists(name)) {
				String perm = Util.format("blobcatraz.kits.%1s", name);
				if(p.hasPermission(perm)) {
					PlayerInventory pi = p.getInventory();
					List<ItemStack> list = ConfigKits.kit(name);
					boolean full = false;
					List<ItemStack> drop = Util.newList();
					for(ItemStack is : list) {
						Map<Integer, ItemStack> map = pi.addItem(is);
						if(map.isEmpty()) continue;
						else {
							full = true;
							drop.addAll(map.values());
						}
					}
					
					if(full) {
						String error = prefix + "Your inventory is full, dumping items on the floor...";
						p.sendMessage(error);
						Location l = p.getLocation();
						World w = l.getWorld();
						for(ItemStack is : drop) w.dropItem(l, is);
					}
					
					String msg = Util.format(prefix + "You received a kit called '%1s'", name);
					p.sendMessage(msg);
				} else {
					String error = Util.format(prefix + Language.NO_PERMISSION, perm);
					p.sendMessage(error);
				}
			} else {
				String error = Util.format(prefix + "Invalid Kit '%1s'.", name);
				p.sendMessage(error);
			}
		}
	}
	
	private void list(Player p) {
		
	}
}