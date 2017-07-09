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
import java.util.Map;

public class ListenAutoPickup implements Listener {
	private static Map<Player, ItemStack> NOPICKUP = Util.newMap();

	@EventHandler(priority=EventPriority.HIGHEST)
	public void drop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		Item i = e.getItemDrop();
		ItemStack is = i.getItemStack();
		NOPICKUP.put(p, is);
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
				Player p = closest(i);
				if(p != null) {
					if(NOPICKUP.containsKey(p)) {
						ItemStack is2 = NOPICKUP.get(p);
						if(is.equals(is2)) {
							NOPICKUP.remove(p);
							return;
						}
					}

					PlayerInventory pi = p.getInventory();
					Map<Integer, ItemStack> add = pi.addItem(is);
					e.setCancelled(true);
					if(!add.isEmpty()) {
						String msg = "&3\u272A&6 Inventory Full &3\u272A";
						PlayerUtil.title(p, "", msg);
						PlayerUtil.ping(p);
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