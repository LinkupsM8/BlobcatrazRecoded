package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

/**
 * This command prevents random people from doing /help
 * and seeing all the plugins on the server by overriding
 * the default /help
 * @author SirBlobman
 */
public class CommandHelp extends ICommand {
	public CommandHelp() {super("help", "", "blobcatraz.player.help", "?", "ayuda", "assistance", "about");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		cs.sendMessage("Ask a staff member for help if you don't know what commands to use!");
		cs.sendMessage("Basic Commands: ");
		String[] commands = new String[] {
			"&l/warp - &rWarp somewhere (buggy)",
			"&l/island - &rGo to your island (working on it)",
			"&l/msg - &rSend a private message to a friend",
			"&l/afk - &rAway From Keyboard",
			"&l/balance - &rCheck how much money is in your piggy bank",
			"&l/baltop - &rSee who has the most money on the server!",
			"&l/pay - &rPay someone out of your piggy bank! Make sure you have enough",
			"&l/hub - &rTeleport to the Hub"
		};
		cs.sendMessage(Util.color(commands));
	}
}