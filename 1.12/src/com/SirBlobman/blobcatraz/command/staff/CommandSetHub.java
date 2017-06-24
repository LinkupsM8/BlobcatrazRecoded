package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSetHub extends PlayerCommand {
	public static Location HUB = ConfigBlobcatraz.get(Location.class, "hub");
	public CommandSetHub() {super("sethub", "", "blobcatraz.staff.sethub", "setlobby");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		ConfigBlobcatraz.set("hub", l, true);
		ConfigBlobcatraz.save();
		HUB = ConfigBlobcatraz.get(Location.class, "hub");
		String msg = prefix + "You changed the hub to \n" + Util.str(HUB);
		p.sendMessage(msg);
	}
}