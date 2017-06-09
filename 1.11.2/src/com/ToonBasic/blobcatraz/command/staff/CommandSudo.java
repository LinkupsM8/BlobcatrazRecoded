package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandSudo extends ICommand {

	public CommandSudo() {super("sudo", "<player> <message/command>", "blobcatraz.staff.sudo");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {	
		if (args.length > 1) {	
			Player p = Bukkit.getPlayer(args[0]);
			if (p != null) {
				String sudo = Util.finalArgs(1, args);
				if (sudo.startsWith("/")) {
					String cmd = sudo.substring(1);
					p.performCommand(cmd);
				} else p.chat(sudo);
			} else {
				cs.sendMessage("\u00a7cThat player does not exist or is not online!");
				return;
			}

		} else {
			String error = "Missing Arguments! Try /sudo <player> <msg or command>";
			cs.sendMessage(error);
		}
	}

}
