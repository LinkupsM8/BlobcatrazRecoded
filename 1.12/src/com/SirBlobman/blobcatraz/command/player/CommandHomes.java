package com.SirBlobman.blobcatraz.command.player;

import java.util.List;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandHomes extends PlayerCommand {
	public CommandHomes() {super("homes", "", "blobcatraz.player.home");}
	
	@Override
	public void run(Player p, String[] args) {
		List<String> list = ConfigDatabase.homes(p);
		String h = Util.format("&2%1s", Util.joinList(list, "&r, &2"));
		String[] msg = Util.color(prefix + "Homes:", h);
		p.sendMessage(msg);
	}
}