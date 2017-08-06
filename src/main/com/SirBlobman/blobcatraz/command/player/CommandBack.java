package com.SirBlobman.blobcatraz.command.player;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandBack extends PlayerCommand implements Listener {
	public static Map<Player, Location> BACK = Util.newMap();
	public CommandBack() {super("back", "", "blobcatraz.player.back", "return", "goback");}
	
	@Override
	public void run(Player p, String[] args) {
		if(BACK.containsKey(p)) {
			Location l = BACK.get(p);
			p.teleport(l);
			String msg = "Welcome Back!";
			p.sendMessage(msg);
		} else {
			String error = "You don't have a place to return to";
			p.sendMessage(error);
		}
	}
	
	@EventHandler
	public void tp(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		Location l = e.getFrom();
		BACK.put(p, l);
	}
	
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Location l = p.getLocation();
		BACK.put(p, l);
	}
}