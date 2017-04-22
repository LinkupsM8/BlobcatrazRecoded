package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandPTime extends ICommand {
	private static final int DAY = 6000;
	private static final int NIGHT = 18000;
	
	public CommandPTime() {super("ptime", "<day/night/reset>", "blobcatraz.special.pweather");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String sub = args[0].toLowerCase();
		if(sub.equals("day")) {
			p.setPlayerTime(DAY, false);
			String msg = prefix + "You will now see day forever!";
			p.sendMessage(msg);
		} else if(sub.equals("night")) {
			p.setPlayerTime(NIGHT, false);
			String msg = prefix + "You will now see night forever!";
			p.sendMessage(msg);
		} else if(sub.equals("reset")) {
			p.resetPlayerTime();
			String msg = prefix + "Your time is now the same as the server!";
			p.sendMessage(msg);
		} else {
			String error = prefix + Language.INCORRECT_USAGE;
			p.sendMessage(error);
		}
	}
}