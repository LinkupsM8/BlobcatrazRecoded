package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.command.ICommand.Language;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class ListenPortal implements Listener {
	public static final ItemStack WAND = ItemUtil.newItem(Material.BLAZE_ROD, 1, 0, "&2Portal Wand");
	private static Map<UUID, Location> POS1 = Util.newMap();
	private static Map<UUID, Location> POS2 = Util.newMap();
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		for(String name : ConfigPortals.portalList()) {
			Location p1 = ConfigPortals.primary(name);
			Location p2 = ConfigPortals.secondary(name);
			if(PlayerUtil.within(p, p1, p2)) {
				Location d = ConfigPortals.destination(name);
				p.teleport(d);
			}
		}
	}
	
	@EventHandler
	public void portal(PlayerPortalEvent e) {
		Location l = e.getFrom();
		World w = l.getWorld();
		String name = w.getName();
		if(name.equals("Hub")) e.setCancelled(true);
	}
	
	@EventHandler
	public void wand(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		UUID uuid = p.getUniqueId();
		ItemStack is = e.getItem();
		if(!ItemUtil.air(is) && is.equals(WAND)) {
			e.setCancelled(true);
			String perm = "blobcatraz.staff.portal.wand";
			if(p.hasPermission(perm)) {
				if(a == Action.LEFT_CLICK_BLOCK) {
					Block b = e.getClickedBlock();
					Location l = b.getLocation();
					POS1.put(uuid, l);
					String msg = Util.color("&2Primary &rlocation set!");
					p.sendMessage(msg);
				} else if(a == Action.RIGHT_CLICK_BLOCK) {
					Block b = e.getClickedBlock();
					Location ll = b.getLocation();
					Location pl = primary(uuid);
					if(pl != null) {
						World w1 = pl.getWorld(); String n1 = w1.getName();
						World w2 = ll.getWorld(); String n2 = w2.getName();
						if(n1.equals(n2)) {
							POS2.put(uuid, ll);
							String msg = Util.color("&2Secondary &rlocation set!");
							p.sendMessage(msg);
						} else {
							String error = "The secondary location must be in the same world as the primary!";
							p.sendMessage(error);
						}
					} else {
						String error = "You must set the primary location before the secondary!";
						p.sendMessage(error);
					}
				}
			} else {
				String error = Util.format(Util.PREFIX + Language.NO_PERMISSION, perm);
				p.sendMessage(error);
			}
		}
	}
	
	public static Location primary(UUID uuid) {
		if(POS1.containsKey(uuid)) {
			Location l = POS1.get(uuid);
			return l;
		} else return null;
	}
	
	public static Location secondary(UUID uuid) {
		if(POS2.containsKey(uuid)) {
			Location l = POS2.get(uuid);
			return l;
		} else return null;
	}
}