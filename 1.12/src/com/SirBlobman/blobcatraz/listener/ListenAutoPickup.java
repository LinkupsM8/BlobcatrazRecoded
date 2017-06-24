package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class ListenAutoPickup implements Listener {
	private static List<ItemStack> NOPICKUP = Util.newList();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void drop(PlayerDropItemEvent e) {
		Item i = e.getItemDrop();
		ItemStack is = i.getItemStack();
		NOPICKUP.add(is);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void item(EntitySpawnEvent e) {
		Entity en = e.getEntity();
		World w = en.getWorld();
		String n = w.getName();
		if(n.equals("Mines")) {
			EntityType et = en.getType();
			if(et == EntityType.DROPPED_ITEM) {
				Item i = (Item) en;
				ItemStack is = i.getItemStack();
				if(!NOPICKUP.contains(is)) {
					Player p = closest(i);
					if(p != null) {
						PlayerInventory pi = p.getInventory();
						int slot = pi.firstEmpty();
						if(slot == -1) {
							String msg = "Your inventory is too full!";
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
	}
	
	private Player closest(Entity en) {
		List<Entity> ee = en.getNearbyEntities(5, 5, 5);
		List<Player> near = players(ee);
		if(near.isEmpty()) return null;
		else {
			Location o = en.getLocation();
			Player c = near.get(0);
			double dist = c.getLocation().distanceSquared(o);
			for(Player p : near) {
				Location l = p.getLocation();
				if(l.distanceSquared(o) < dist) {
					dist = l.distanceSquared(o);
					c = p;
				}
			}
			return c;
		}
	}
	
	private List<Player> players(List<Entity> ee) {
		List<Player> list = Util.newList();
		for(Entity e : ee) {
			if(e instanceof Player) {
				Player p = (Player) e;
				list.add(p);
			}
		}
		return list;
	}
}