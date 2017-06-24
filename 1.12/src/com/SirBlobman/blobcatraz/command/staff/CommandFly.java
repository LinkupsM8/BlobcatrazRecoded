package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandFly extends PlayerCommand {
	public CommandFly() {super("fly", "<on/off>", "blobcatraz.staff.fly");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("on")) {
			p.setAllowFlight(true);
			p.setFlying(true);
			String msg = Util.color(prefix + "&fYour flight is now enabled");
			p.sendMessage(msg);
		} else if(sub.equals("off")) {
			p.setAllowFlight(false);
			p.setFlying(false);
			String msg = Util.color(prefix + "&fYour flight is now disabled");
			p.sendMessage(msg);
		} else {
			String error = getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}