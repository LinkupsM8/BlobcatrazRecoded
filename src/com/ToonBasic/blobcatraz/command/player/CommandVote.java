package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandVote extends ICommand {
	private static List<String> list = Util.newList(
		"&a&nLink 1",
		"&b&nLink 2",
		"&c&nLink 3"
	);
	public CommandVote() {super("vote", "", "blobcatraz.player.vote", "votelinks", "links");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String nlist = Util.formatList('-', list);
		String[] msg = Util.color(prefix + "Vote Links:", nlist);
		cs.sendMessage(msg);
	}
}
