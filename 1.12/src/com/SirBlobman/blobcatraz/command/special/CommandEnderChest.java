package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

public class CommandEnderChest extends PlayerCommand {
	public CommandEnderChest() {super("enderchest", "[player]", "blobcatraz.special.enderchest", "echest", "ec");}
	
	@Override
	public void run(Player p, String[] args) {
		String other = "blobcatraz.special.enderchest.other";
		if(p.hasPermission(other) && args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) open(p, t);
			else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else open(p, p);
	}
	
	private void open(Player p, Player t) {
		Inventory i = t.getEnderChest();
		p.openInventory(i);
		String name = t.getName();
		String poss = WordUtil.possessive(name);
		String msg = Util.format(prefix + "Opening &a%1s&r enderchest...", poss);
		p.sendMessage(msg);
	}
}