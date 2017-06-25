package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.entity.Player;

public class CommandPTime extends PlayerCommand {
	private static final int DAWN = 0;
	private static final int NOON = 6000;
	private static final int SUNSET = 12000;
	private static final int MIDNIGHT = 18000;
	public CommandPTime() {super("ptime", "<dawn/noon/sunset/reset>", "blobcatraz.special.ptime");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("dawn")) {
			p.setPlayerTime(DAWN, false);
			String msg = prefix + "It is now dawn forever!";
			p.sendMessage(msg);
		} else if(sub.equals("noon")) {
			p.setPlayerTime(NOON, false);
			String msg = prefix + "It is now noon forever!";
			p.sendMessage(msg);
		} else if(sub.equals("sunset")) {
			p.setPlayerTime(SUNSET, false);
			String msg = prefix + "It is now sunset forever!";
			p.sendMessage(msg);
		} else if(sub.equals("midnight")) {
			p.setPlayerTime(MIDNIGHT, false);
			String msg = prefix + "It is now midnight forever!";
			p.sendMessage(msg);
		} else if(sub.equals("reset")) {
			p.resetPlayerTime();
			String msg = prefix + "Your time is now the same as the server.";
			p.sendMessage(msg);
		} else {
			String error = prefix + getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}