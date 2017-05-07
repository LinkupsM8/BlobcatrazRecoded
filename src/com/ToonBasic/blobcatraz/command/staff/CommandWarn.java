package com.ToonBasic.blobcatraz.command.staff;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandWarn extends ICommand {
	public CommandWarn() {super("warn", "<player> <reason>", "blobcatraz.staff.warn");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t == null) {
			String error = prefix + Language.INVALID_TARGET;
			cs.sendMessage(error);
		} else {
			String reason = Util.finalArgs(1, args);
			ConfigDatabase.addWarning(t, reason);
			List<String> list = ConfigDatabase.warnings(t);
			int amount = list.size();
			if(amount >= 3) {
				String nreason = "You got 3 warnings:" 
						+ "\n&a1) " + list.get(0)
						+ "\n&a2) " + list.get(1)
						+ "\n&a3) " + list.get(2);
				Date d = CommandTempBan.date("1d");
				CommandTempBan.tempBan(t, cs.getName(), d, nreason);
				ConfigDatabase.clearWarnings(t);
			}
			String msg = prefix + "You warned " + t.getName();
			String msg2 = Util.color("&4[&cWarning&4]&f " + reason);
			String msg3 = Util.color("You now have " + amount + " warnings!");
			cs.sendMessage(msg);
			t.sendMessage(msg2);
			t.sendMessage(msg3);
		}
	}
}