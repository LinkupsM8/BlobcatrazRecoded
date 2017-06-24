package com.SirBlobman.blobcatraz.command.player;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandDeleteHome extends PlayerCommand {
	public CommandDeleteHome() {super("deletehome", "<name>", "blobcatraz.player.deletehome", "delhome", "remhome", "removehome");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		if(ConfigDatabase.homeExists(p, name)) {
			ConfigDatabase.delHome(p, name);
			String msg = Util.format(prefix + "You deleted your home called &2%1s.", name);
			p.sendMessage(msg);
		} else {
			String error = Util.format(prefix + "You don't have a home called '%1s'.", name);
			p.sendMessage(error);
		}
	}
}