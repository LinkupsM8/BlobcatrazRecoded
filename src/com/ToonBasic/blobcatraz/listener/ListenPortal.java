package com.ToonBasic.blobcatraz.listener;

import java.util.Map;
import java.util.UUID;

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
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenPortal implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		for (String name : ConfigPortals.getPortalList()) {
			
			Player p = e.getPlayer();
			Location pl = p.getLocation();
			World pw = pl.getWorld();
			Location primary = ConfigPortals.getPrimary(name);
			Location secondary = ConfigPortals.getSecondary(name);
			
			if (pw.equals(primary.getWorld())) {
				if (PlayerUtil.within(p, primary, secondary)) {
					Location l = ConfigPortals.getDestination(name);
					p.teleport(l);
				}
			}
		}
	}
	
	private static Map<UUID, Location> primary = Util.newMap();
	private static Map<UUID, Location> secondary = Util.newMap();
	
	public static final ItemStack wand() {	
		ItemStack wand = new ItemStack(Material.BLAZE_ROD);
		ItemMeta im = wand.getItemMeta();
		im.setDisplayName(Util.color("&2Portal Wand"));
		wand.setItemMeta(im);
		return wand;
	}
	
	@EventHandler
	public void antiNether(PlayerPortalEvent e) {
		Location from = e.getFrom();
		World w = from.getWorld();
		String name = w.getName().toLowerCase();
		if(name.equals("hub")) e.setCancelled(true);
	}
	
	@EventHandler
	public void wand(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Action a = e.getAction();
		ItemStack is = e.getItem();
		if(is.equals(wand())) {
			e.setCancelled(true);
			String perm = "blobcatraz.special.portal.wand";
			if(p.hasPermission(perm)) {
				if(a == Action.LEFT_CLICK_BLOCK) {
					Block b = e.getClickedBlock();
					Location l = b.getLocation();
					primary.put(uuid, l);
					String msg = Util.color("&2Primary &rlocation set!");
					p.sendMessage(msg);
				} else if(a == Action.RIGHT_CLICK_BLOCK) {
					Block b = e.getClickedBlock();
					Location l = b.getLocation();
					Location pl = getPrimary(uuid);
					if(p != null && !l.getWorld().equals(pl.getWorld())) {
						String error = Util.color("The &2Secondary &rlocation must be set in the same world as the &2Primary &rlocation");
						p.sendMessage(error);
					} else {
						secondary.put(uuid, l);
						String msg = Util.color("&2Secondary &rlocation set!");
						p.sendMessage(msg);
					}
				}
			}
		}
	}

	public static Location getPrimary(UUID uuid) {
		if(primary.containsKey(uuid)) {
			Location l = primary.get(uuid);
			return l;
		} else return null;
	}
	
	public static Location getSecondary(UUID uuid) {
		if(secondary.containsKey(uuid)) {
			Location l = secondary.get(uuid);
			return l;
		} else return null;
	}
}
