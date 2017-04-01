package com.ToonBasic.blobcatraz.command.player;

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
		if (args.length == 0) {
			if (!ConfigDatabase.homeExists(p, "home")) {
				p.sendMessage(prefix + "Could not find a home!");
				return;
			}
			p.teleport(ConfigDatabase.getHome(p, "home"));
		} else if (args.length > 0) {
			String name = Util.finalArgs(0, args);
			if (!ConfigDatabase.homeExists(p, name)) {
				p.sendMessage(prefix + "Could not find a home!");
				return;
			}
			p.teleport(ConfigDatabase.getHome(p, name));
		}
	}
}