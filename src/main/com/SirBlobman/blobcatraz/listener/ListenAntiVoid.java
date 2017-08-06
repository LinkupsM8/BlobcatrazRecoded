package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenAntiVoid implements Listener {
	@EventHandler
	public void fall(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		World w = p.getWorld();
		String name = w.getName();
		if(name.equals("Hub")) {
			Location l = e.getTo();
			double y = l.getY();
			if(y < 0.0D) p.performCommand("hub");
		}
	}
}