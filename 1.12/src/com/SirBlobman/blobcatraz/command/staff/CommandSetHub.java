package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class CommandSetHub extends PlayerCommand {
	public static Location HUB = ConfigBlobcatraz.get(Location.class, "hub");
	public CommandSetHub() {super("sethub", "", "blobcatraz.staff.sethub", "setlobby");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		ConfigBlobcatraz.set("hub", l, true);
		ConfigBlobcatraz.save();
		HUB = ConfigBlobcatraz.get(Location.class, "hub");
	}
}