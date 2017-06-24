package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandCreateKit extends PlayerCommand {
	public CommandCreateKit() {super("createkit", "<name>", "blobcatraz.staff.createkit", "setkit", "addkit", "makekit");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		PlayerInventory pi = p.getInventory();
		ConfigKits.create(name, pi);
		String msg = Util.format(prefix + "You created a kit called &b%1s", name);
		p.sendMessage(msg);
	}
}