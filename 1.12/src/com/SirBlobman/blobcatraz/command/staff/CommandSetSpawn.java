package com.SirBlobman.blobcatraz.command.staff;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandSetSpawn extends PlayerCommand {
	public CommandSetSpawn() {super("setspawn", "", "blobcatraz.staff.setspawn");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		World w = l.getWorld();
		String name = w.getName();
		String path = "spawn." + name;
		
		ConfigBlobcatraz.set(path, l, true);
		ConfigBlobcatraz.save();
		String msg = Util.format(prefix + "You changed the spawn of %1s to %2s", name, Util.str(l));
		p.sendMessage(msg);
	}
}