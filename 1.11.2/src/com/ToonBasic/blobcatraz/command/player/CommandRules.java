package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandRules extends ICommand {
	private static final String[] RULES = Util.color(
		" ",
		"\n&c-1) &bSirBlobman is the owner, ToonBasic is the Co Owner.\n&eToonBasic is also the staff manager",
		"&c0) &bFollow all the rules, including this one!",
		"&a1) &bDo not use cheats (hacks) or unapproved mods.\n&eOptifine is OK",
		"&a2) &bDo not grief or build offensive things",
		"&a3) &bHave common sense and be polite!",
		"&a4) &bIf you have fly perms, do not fly out of the mines!",
		"&a5) &bDo not abuse bugs or glitches.",
		"&a6) &bDo not give away your money, let new players do some work.",
		"&a7) &bDo not impersonate staff.",
		" ",
		"&eThe staff have the final say in whether you get banned or not. "
		+ "If you break any of these rules you can be "
		+ "muted, kicked, temp-banned, or perma-banned, depending on the severity. "
		+ "If you do get banned, you may appeal at &3&n&ohttp://blobcatraz.mc-srv.com\n&f",
		" "
	);
	public CommandRules() {super("rules", "", "blobcatraz.player.rules");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {cs.sendMessage(RULES);}
}