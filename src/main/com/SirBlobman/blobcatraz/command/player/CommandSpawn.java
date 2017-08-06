package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandSpawn extends PlayerCommand {
	public CommandSpawn() {super("spawn", "", "blobcatraz.player.spawn");}
	
	@Override
	public void run(Player p, String[] args) {
		World w = p.getWorld();
		String name = w.getName();
		String path = "spawn." + name;
		Location l = ConfigBlobcatraz.get(Location.class, path);
		if(l == null) l = defaultSpawn(w);
		p.teleport(l);
		String msg = "You are now at spawn!";
		p.sendMessage(msg);
	}
	
	private Location defaultSpawn(World w) {
		int x = 0;
		int y = 64;
		int z = 0;
		Location l = new Location(w, x, y, z);
		return l;
	}
}