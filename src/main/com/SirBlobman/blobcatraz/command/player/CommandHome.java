package com.SirBlobman.blobcatraz.command.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandHome extends PlayerCommand {
	public CommandHome() {super("home", "[name]", "blobcatraz.player.home");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = "home";
		if(args.length > 0) name = Util.finalArgs(0, args);
		
		if(ConfigDatabase.homeExists(p, name)) {
			Location l = ConfigDatabase.home(p, name);
			p.teleport(l);
			String msg = "Welcome home!";
			p.sendMessage(msg);
		} else {
			String error = Util.format(prefix + "You don't have a home called '%1s'", name);
			p.sendMessage(error);
		}
	}
}