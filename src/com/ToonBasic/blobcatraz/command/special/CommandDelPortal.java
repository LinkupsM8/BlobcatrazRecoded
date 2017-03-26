package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandDelPortal extends ICommand {
	
	public CommandDelPortal() {
		super("delportal", "<name>", "blobcatraz.special.delportal", "deleteportal", "removeportal", "remportal");
	}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
		String name = Util.finalArgs(0, args);
		if (!ConfigPortals.exists(name)) {
			cs.sendMessage(prefix + "Could not find a portal!");
			return;
		}
		ConfigPortals.delete(name);
		cs.sendMessage("Portal \u00a72" + name + " \u00a7rwas deleted!");
		
	}
}