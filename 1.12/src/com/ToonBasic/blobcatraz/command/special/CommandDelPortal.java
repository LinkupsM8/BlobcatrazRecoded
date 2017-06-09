package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandDelPortal extends ICommand {
	
	public CommandDelPortal() {
		super("delportal", "<name>", "blobcatraz.special.delportal", "deleteportal", "removeportal", "remportal");
	}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String name = Util.finalArgs(0, args);
		if (!ConfigPortals.exists(name)) {
			p.sendMessage(prefix + "Could not find a portal!");
			return;
		}
		ConfigPortals.delete(name);
		p.sendMessage(prefix + "Portal \u00a72" + name + " \u00a7rwas deleted!");
	}
}