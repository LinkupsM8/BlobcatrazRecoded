package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandPrefix extends ICommand implements Listener {
	public CommandPrefix() {super("prefix", "<player> <prefix>", "blobcatraz.staff.prefix");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t == null) {
			String msg = "Your target is not online, changing your prefix instead!";
			cs.sendMessage(msg);
			t = (Player) cs;
		}
		
		String prefix2 = Util.finalArgs(1, args) + " &f";
		String pref = Util.color(prefix2);
		t.setDisplayName(pref + ConfigDatabase.nickName(t));
		ConfigDatabase.prefix(t, prefix2);
		cs.sendMessage(prefix + "You changed the prefix of " + t.getName() + " to " + pref);
		t.sendMessage(prefix + "Your prefix was changed to " + pref);
	}
}