package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandPrefix extends ICommand implements Listener {
	public CommandPrefix() {super("prefix", "<prefix>", "blobcatraz.staff.prefix");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String prefix = Util.finalArgs(0, args) + " &f";
		String pref = Util.color(prefix);
		Player p = (Player) cs;
		p.setDisplayName(pref + ConfigDatabase.nickName(p));
		ConfigDatabase.prefix(p, prefix);
		p.sendMessage("Your prefix was changed to " + pref);
	}
}