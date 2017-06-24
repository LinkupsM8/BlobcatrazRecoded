package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandChatControl extends ICommand {
	public static boolean mute = false;
	public CommandChatControl() {super("chatcontrol", "<enable/disable/clear>", "blobcatraz.staff.chatcontrol", "chat", "cc", "ch");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String name = cs.getName();
		String sub = args[0].toLowerCase();
		if(sub.equals("disable")) {
			mute = true;
			String msg = prefix + "The chat is now muted!";
			cs.sendMessage(msg);
			Util.broadcast(prefix + "The chat was muted by &b" + name);
		} else if(sub.equals("enable")) {
			mute = false;
			Util.broadcast(prefix + "The chat is no longer muted.");
		} else if(sub.equals("clear")) {
			for(int i = 0; i < 500; i++) {Util.broadcast(" ");}
			Util.broadcast(
				"|-------------------+====+-------------------|",
				"The chat was cleared by &b" + name + "&r.",
				"|-------------------+====+-------------------|"
			);
		}
	}
}