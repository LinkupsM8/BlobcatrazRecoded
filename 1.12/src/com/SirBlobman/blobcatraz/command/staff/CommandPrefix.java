package com.SirBlobman.blobcatraz.command.staff;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPrefix extends ICommand {
	public CommandPrefix() {super("prefix", "<player> <prefix>", "blobcatraz.staff.prefix");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String tname = t.getName();
			String pre1 = Util.finalArgs(1, args) + " &f";
			String pre2 = Util.color(pre1);
			String nick = ConfigDatabase.nick(t);
			ConfigDatabase.setPrefix(t, pre1);
			t.setDisplayName(pre2 + nick);
			String msg1 = Util.format(prefix + "You changed the prefix of %1s to %2s", tname, pre2);
			String msg2 = Util.format(prefix + "Your prefix was changed to %1s", pre2);
			cs.sendMessage(msg1);
			t.sendMessage(msg2);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
}