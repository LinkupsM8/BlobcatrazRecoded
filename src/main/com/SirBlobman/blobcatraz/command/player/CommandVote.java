package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandVote extends ICommand {
	private static List<String> LINKS = ConfigBlobcatraz.getList(String.class, "vote links");
	public CommandVote() {super("vote", "", "blobcatraz.player.vote", "votelinks", "links");}
	
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String nlist = Util.joinList(LINKS, "\n- ", 0);
		String msg = Util.format(prefix + "Vote Links: %1s", nlist);
		cs.sendMessage(msg);
	}
}