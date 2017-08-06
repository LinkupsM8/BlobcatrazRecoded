package com.SirBlobman.blobcatraz.command.staff;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class CommandGod extends PlayerCommand implements Listener {
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
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void hurt(EntityDamageEvent e) {
		Entity en = e.getEntity();
		if(en instanceof Player) {
			Player p = (Player) en;
			if(gods.contains(p)) e.setCancelled(true);
		}
	}
}