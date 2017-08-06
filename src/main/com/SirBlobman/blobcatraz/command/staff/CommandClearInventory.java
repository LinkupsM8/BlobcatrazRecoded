package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandClearInventory extends PlayerCommand {
	public CommandClearInventory() {super("clear", "[player]", "blobcatraz.staff.clear", "clearinventory", "ci");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				clear(t);
				String msg = prefix + "You cleared the inventory of " + t.getName();
				p.sendMessage(msg);
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else clear(p);
	}
	
	private void clear(Player p) {
		PlayerInventory pi = p.getInventory();
		pi.clear();
		String msg = prefix + "Your inventory is now empty.";
		p.sendMessage(msg);
	}
}