package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;

public class CommandDeletePortal extends PlayerCommand {
	public CommandDeletePortal() {super("deleteportal", "<name>", "blobcatraz.special.portal", "delportal", "remportal", "removeportal");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		if(ConfigPortals.exists(name)) {
			ConfigPortals.delete(name);
			String msg = Util.format(prefix + "You deleted a portal called &2%1s", name);
			p.sendMessage(msg);
		} else {
			String error = Util.format(prefix + "The portal '%1s' doesn't exist!", name);
			p.sendMessage(error);
		}
	}
}