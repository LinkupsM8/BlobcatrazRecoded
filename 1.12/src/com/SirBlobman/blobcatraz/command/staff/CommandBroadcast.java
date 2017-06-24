package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandBroadcast extends ICommand {
	public CommandBroadcast() {super("broadcast", "<msg>", "blobcatraz.staff.broadcast", "bcast", "bc", "shout");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String msg = Util.finalArgs(0, args);
		Util.broadcast(msg);
	}
}