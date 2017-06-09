package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.WordUtil;

public class CommandWhoIs extends ICommand {
	public CommandWhoIs() {super("whois", "<nickname>", "blobcatraz.staff.whois", "realname");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String nick = Util.finalArgs(0, args);
		for(Player o : Bukkit.getOnlinePlayers()) {
			String disp = o.getDisplayName();
			disp = Util.strip(disp);
			if(disp.contains(nick)) {
				String d = o.getDisplayName();
				String name = o.getName();
				String msg = prefix + Util.color(WordUtil.possesive(d) + " &foriginal name is " + name);
				cs.sendMessage(msg);
				break;
			}
		}
	}
}