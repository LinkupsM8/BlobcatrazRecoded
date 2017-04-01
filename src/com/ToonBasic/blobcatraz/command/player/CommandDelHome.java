package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandDelHome extends ICommand {
	
	public CommandDelHome() {
		super("delhome", "[name]", "blobcatraz.player.delhome", "deletehome", "removehome", "remhome");
	}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String name = Util.finalArgs(0, args);
		if (!ConfigDatabase.homeExists(p, name)) {
			p.sendMessage(prefix + "Could not find a home!");
			return;
		}
		ConfigDatabase.delHome(p, name);
		p.sendMessage(prefix + "Home \u00a72" + name + " \u00a7rwas deleted!");
	}
}