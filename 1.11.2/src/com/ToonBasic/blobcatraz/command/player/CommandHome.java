package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandHome extends ICommand {
	public CommandHome() {super("home", "[name]", "blobcatraz.player.home");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String name = "home";
		if(args.length > 0) name = Util.finalArgs(0, args);
		
		if (ConfigDatabase.homeExists(p, name)) {
			Location home = ConfigDatabase.getHome(p, name);
			String msg = "Welcome home!";
			p.teleport(home);
			p.sendMessage(msg);
		} else {
			String error = prefix + "You don't have a home named '" + name + "'.";
			p.sendMessage(error);
		}
	}
}