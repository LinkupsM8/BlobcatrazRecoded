package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandBurn extends ICommand {
	public CommandBurn() {super("burn", "<player> <time>", "blobcatraz.special.burn");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t == null) {
			String error = Language.INVALID_TARGET;
			cs.sendMessage(error);
		} else {
			String time = args[1];
			try {
				time = NumberUtil.onlyInteger(time);
				int ti = Integer.parseInt(time);
				t.setFireTicks(ti);
				String msg = Util.color(prefix + "You set &a" + t.getName() + "&r on fire for &a" + ti + "&r ticks");
				cs.sendMessage(msg);
			} catch(Exception ex) {
				String error = "Invalid Number: " + time;
				cs.sendMessage(error);
			}
		}
	}
}