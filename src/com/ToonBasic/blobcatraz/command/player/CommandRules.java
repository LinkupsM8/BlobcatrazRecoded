package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandRules extends ICommand {
	String[] rules = Util.color(new String[] {
		"\n&c-1) &bSirBlobman is the owner, Gritty is the Co Owner.\n&eGritty is also the staff manager",
		"&c0) &bFollow all the rules, including this one!",
		"&a1) &bDo not use cheats (hacks) or unapproved mods.\n&eOptifine is OK",
		"&a2) &bDo not grief or build offensive things",
		"&a3) &bHave common sense and be polite!",
		"&eThe staff have the final say in whether you get banned or not. "
		+ "If you break any of these rules you can be "
		+ "muted, kicked, temp-banned, or perma-banned, depending on the severity. "
		+ "If you do get banned, you may appeal at &3&n&ohttp://blobcatraz.mc-srv.com\n&f"
	});
	public CommandRules() {super("rules", "", "blobcatraz.player.rules");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		cs.sendMessage(rules);
	}
}