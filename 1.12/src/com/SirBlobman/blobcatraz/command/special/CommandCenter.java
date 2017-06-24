package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandCenter extends PlayerCommand {
	public CommandCenter() {super("center", "", "blobcatraz.special.center", "middle", ".5");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		World w = l.getWorld();
		double x = l.getBlockX() + 0.5D;
		double y = l.getBlockY() + 0.5D;
		double z = l.getBlockZ() + 0.5D;
		float yaw = l.getYaw();
		float pit = l.getPitch();
		Location n = new Location(w, x, y, z, yaw, pit);
		p.teleport(n);
		p.sendMessage(prefix + "You are now at the center of this block");
	}
}