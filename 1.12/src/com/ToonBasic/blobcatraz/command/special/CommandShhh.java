package com.ToonBasic.blobcatraz.command.special;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandShhh extends ICommand {
	
	public CommandShhh() { super("shhh", "", ""); }
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String msg = prefix + Util.color("&7Somewhere in the world, "
				+ "a &cToonBasic&7 has suddenly &4&lDIED! &7>:)");
		cs.sendMessage(msg);
		UUID toon = UUID.fromString("4e4bb25a-0245-42c0-9ef5-834c4622fa88");
		OfflinePlayer op = Bukkit.getOfflinePlayer(toon);
		if(op.isOnline()) {
			Player p = op.getPlayer();
			p.setHealth(0.0D);
		}
	}
}