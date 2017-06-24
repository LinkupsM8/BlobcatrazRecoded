package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandGod extends PlayerCommand {
	private static List<Player> gods = Util.newList();
	public CommandGod() {super("god", "", "blobcatraz.staff.god", "jesus", "sirblobman");}
	
	@Override
	public void run(Player p, String[] args) {
		if(gods.contains(p)) {
			gods.remove(p);
			String msg = prefix + "You are now a mortal.";
			p.sendMessage(msg);
		} else {
			gods.add(p);
			String msg = prefix + "You are now a god!";
			p.sendMessage(msg);
		}
	}
	
	@EventHandler
	public void hurt(EntityDamageEvent e) {
		Entity en = e.getEntity();
		if(en instanceof Player) {
			Player p = (Player) e;
			if(gods.contains(p)) e.setCancelled(true);
		}
	}
}