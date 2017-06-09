package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandHomes extends ICommand {
	public CommandHomes() {super("homes", "", "blobcatraz.player.home");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		List<String> list = ConfigDatabase.getHomes(p);
		String homes = "&2" + Util.listToString(list, "&r, &2");
		String[] msg = Util.color(prefix + "Homes:", homes);
		p.sendMessage(msg);
	}
}