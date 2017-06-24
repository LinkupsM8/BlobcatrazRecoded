package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSudo extends ICommand {
	public CommandSudo() {super("sudo", "<player> <msg/command>", "blobcatraz.staff.sudo", "force");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String sudo = Util.finalArgs(1, args);
			if(sudo.startsWith("/")) {
				String cmd = sudo.substring(1);
				t.performCommand(cmd);
			} else t.chat(sudo);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
}