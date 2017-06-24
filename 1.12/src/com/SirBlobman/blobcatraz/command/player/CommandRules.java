package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandRules extends ICommand {
	private static final List<String> RULES = ConfigBlobcatraz.getList(String.class, "rules");
	public CommandRules() {super("rules", "", "blobcatraz.player.rules");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		for(String s : RULES) {
			String c = Util.color(s);
			cs.sendMessage(c);
		}
	}
}