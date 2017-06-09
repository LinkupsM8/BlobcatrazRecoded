package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigWarps;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandDelWarp extends ICommand {
	public CommandDelWarp() {super("delwarp", "<name>", "blobcatraz.staff.delwarp", "deletewarp", "removewarp", "remwarp");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String name = Util.finalArgs(0, args);
		ConfigWarps.delete(name);
		cs.sendMessage("Warp §2" + name + " §rwas deleted");
	}
}