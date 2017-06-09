package com.ToonBasic.blobcatraz.listener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;


public class ListenAutoPickup implements Listener {
	@EventHandler
	public void item(EntitySpawnEvent e) {
		Entity ent = e.getEntity();
		World w = ent.getWorld();
		String name = w.getName();
		if(name.equals("Mines")) {
			EntityType et = ent.getType();
			if(et == EntityType.DROPPED_ITEM) {
				Item i = (Item) ent;
				Player p = closest(i);
				if(p != null) {
					PlayerInventory pi = p.getInventory();
					ItemStack is = i.getItemStack();
					int slot = pi.firstEmpty();
					if(slot == -1) {
						String msg = "Your inventory is full!";
						PlayerUtil.action(p, msg);
						PlayerUtil.ping(p);
					} else {
						e.setCancelled(true);
						pi.addItem(is);
					}
				}
			}
		}
	}
	
	private Player closest(Entity e) {
		List<Entity> ents = e.getNearbyEntities(5, 5, 5);
		List<Player> near = nearbyPlayers(ents);
		if(near.isEmpty()) return null;
		else {
			Location o = e.getLocation();
			Player closest = near.get(0);
			double dist = closest.getLocation().distanceSquared(o);
			for(Player p : near) {
				Location l = p.getLocation();
				if(l.distanceSquared(o) < dist) {
					dist = l.distanceSquared(o);
					closest = p;
				}
			}
			return closest;
		}
		}
	
	private List<Player> nearbyPlayers(List<Entity> near) {
		List<Player> list = Util.newList();
		for(Entity e : near) {
			if(e instanceof Player) {
				Player p = (Player) e;
				list.add(p);
			}
		}
		return list;
	}
}