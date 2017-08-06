package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

public class CommandTop extends PlayerCommand {
	public CommandTop() {super("top", "", "blobcatraz.staff.top");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		World w = l.getWorld();
		Block b = w.getHighestBlockAt(l);
		
		int bx = b.getX(), by = b.getY(), bz = b.getZ();
		float pi = l.getPitch(), ya = l.getYaw();
		
		double x = bx + 0.5D, y = by + 1.0D, z = bz + 0.5D;
		Location n = new Location(w, x, y, z, ya, pi);
		p.teleport(n);
		String msg = prefix + "You are now on top of the world";
		p.sendMessage(msg);
	}
}