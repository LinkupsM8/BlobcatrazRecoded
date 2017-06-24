package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandRealName extends ICommand {
	public CommandRealName() {super("realname", "<nick>", "blobcatraz.staff.realname");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String nick = Util.finalArgs(0, args);
		for(Player o : Bukkit.getOnlinePlayers()) {
			String disp = o.getDisplayName();
			String s = Util.strip(disp);
			if(s.contains(nick)) {
				String name = o.getName();
				String msg = Util.color(prefix + name + " is '" + disp + "&f'");
				cs.sendMessage(msg);
				break;
			}
		}
	}
}