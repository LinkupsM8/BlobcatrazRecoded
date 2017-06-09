package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandBroadcast extends ICommand {
	public CommandBroadcast() {super("broadcast", "<message>", "blobcatraz.staff.broadcast", "bcast", "shout");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String msg = Util.finalArgs(0, args);
		Util.broadcast(msg);
	}
}