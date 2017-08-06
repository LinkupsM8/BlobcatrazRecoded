package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandDeleteKit extends PlayerCommand {
	public CommandDeleteKit() {super("deletekit", "<kit>", "blobcatraz.staff.deletekit", "delkit", "removekit", "remkit");}
	
	@Override
	public void run(Player p, String[] args) {
		String kit = Util.finalArgs(0, args);
		ConfigKits.delete(kit);
		String msg = Util.format(prefix + "You deleted a kit called &a%s&r.", kit);
		p.sendMessage(msg);
	}
}