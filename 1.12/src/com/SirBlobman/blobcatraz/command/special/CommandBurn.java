package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBurn extends ICommand {
	public CommandBurn() {super("burn", "<player> <time>", "blobcatraz.special.burn");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String name = t.getName();
			String ti = args[1];
			int time = NumberUtil.getInteger(ti);
			t.setFireTicks(time);
			String msg = Util.format(prefix + "You set &a%1s&r on fire for &a%2s ticks", name, time);
			cs.sendMessage(msg);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
}