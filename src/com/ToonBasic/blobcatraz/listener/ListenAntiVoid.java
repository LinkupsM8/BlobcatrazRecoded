package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ToonBasic.blobcatraz.command.player.CommandHub;

public class ListenAntiVoid implements Listener {
	@EventHandler
	public void fall(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location l = e.getTo();
		World w = l.getWorld();
		String n = w.getName();
		double y = l.getY();
		if(y < 0.0D && n.equals("Hub")) {
			CommandHub.hub(p);
		}
	}
}