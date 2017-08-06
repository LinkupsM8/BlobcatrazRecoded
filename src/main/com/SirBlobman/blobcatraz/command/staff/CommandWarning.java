package com.SirBlobman.blobcatraz.command.staff;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandWarning extends ICommand {
	public CommandWarning() {super("warning", "<player> <reason>", "blobcatraz.staff.warning", "warn");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String reason = Util.finalArgs(1, args);
			ConfigDatabase.addWarning(t, reason);
			List<String> list = ConfigDatabase.warnings(t);
			int amount = list.size();
			if(amount > 2) {
				String r1 = list.get(0);
				String r2 = list.get(1);
				String r3 = list.get(2);
				String s = Util.format("You got 3 warnings: \n&a1) %1s\n&a2) %2s\n&a3) %3s", r1, r2, r3);
				Date d = CommandTempBan.date("1d");
				CommandTempBan.tempban(t, cs.getName(), d, s);
				ConfigDatabase.clearWarnings(t);
			}
			
			String msg1 = prefix + "You warned " + t.getName();
			String msg2 = Util.color("&4[&cWarning&4] &f" + reason);
			String msg3 = "You now have " + amount + " warnings!";
			cs.sendMessage(msg1);
			t.sendMessage(msg2);
			t.sendMessage(msg3);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
}