package com.SirBlobman.blobcatraz.command.player;

import static com.SirBlobman.blobcatraz.command.player.CommandMessage.*;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.CommandSender;

public class CommandReply extends ICommand {
	public CommandReply() {super("reply", "<message>", "blobcatraz.player.message", "r");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		CommandSender ts = REPLY.get(cs);
		if(ts != null) {
			String msg = Util.finalArgs(0, args);
			String c = Util.color(msg);
			msg(cs, ts, c);
		} else {
			String error = prefix + "Nobody has messaged you yet!";
			cs.sendMessage(error);
		}
	}
}