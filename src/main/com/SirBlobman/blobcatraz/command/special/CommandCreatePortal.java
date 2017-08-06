package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.listener.ListenPortal;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandCreatePortal extends PlayerCommand {
	public CommandCreatePortal() {super("createportal", "<name>", "blobcatraz.special.createportal", "setportal");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		Location l = p.getLocation();
		UUID uuid = p.getUniqueId();
		Location p1 = ListenPortal.primary(uuid);
		if(p1 != null) {
			Location p2 = ListenPortal.secondary(uuid);
			if(p2 != null) {
				ConfigPortals.save(name, l, p1, p2);
				String msg = Util.format(prefix + "You set a portal called &2%1s &rto your current location", name);
				p.sendMessage(msg);
			} else {
				String error = prefix + "You must set the secondary wand location before using this command.";
				p.sendMessage(error);
			}
		} else {
			String error = prefix + "You must set the primary wand location before using this command.";
			p.sendMessage(error);
		}
	}
}