package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;

@PlayerOnly
public class CommandHomes extends ICommand {
	public CommandHomes() {super("homes", "", "blobcatraz.player.home");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if (args.length == 0) {
			List<String> list = ConfigDatabase.getHomes(p);
			StringBuilder str = new StringBuilder();
			for(String s : list) {
				str.append(s + "\u00a7r, \u00a72");
			}
			p.sendMessage(prefix + "Home list:");
			p.sendMessage("\u00a72" + str.toString());
		}
	}
}