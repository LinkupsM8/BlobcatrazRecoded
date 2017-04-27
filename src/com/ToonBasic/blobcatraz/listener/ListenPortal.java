package com.ToonBasic.blobcatraz.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenPortal implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		for (String name : ConfigPortals.getPortalList()) {
			
			Player p = e.getPlayer();
			Location primary = ConfigPortals.getPrimary(name);
			Location secondary = ConfigPortals.getSecondary(name);
			
			if (!p.getLocation().getWorld().equals(primary.getWorld())) return;
			
			if (p.getLocation().getX() >= Math.min(primary.getX(), secondary.getX()) - 0.3 &&
				p.getLocation().getX() <= Math.max(primary.getX(), secondary.getX()) + 1.3 &&
				p.getLocation().getY() >= Math.min(primary.getY(), secondary.getY()) &&
				p.getLocation().getY() <= Math.max(primary.getY(), secondary.getY()) &&
				p.getLocation().getZ() >= Math.min(primary.getZ(), secondary.getZ()) - 0.3 &&
				p.getLocation().getZ() <= Math.max(primary.getZ(), secondary.getZ()) + 1.3) {
				
				p.teleport(ConfigPortals.getDestination(name));
				
			}
		}
	}
	
	private static Map<UUID, Location> primary = new HashMap<UUID, Location>();
	private static Map<UUID, Location> secondary = new HashMap<UUID, Location>();
	
	public static final ItemStack wand() {
		
		ItemStack wand = new ItemStack(Material.BLAZE_ROD);
		ItemMeta im = wand.getItemMeta();
		im.setDisplayName("\u00a72Portal Wand");
		wand.setItemMeta(im);
		return wand;
	}
	
	@EventHandler
	public void antiNether(PlayerPortalEvent e) {
		World w = e.getFrom().getWorld();
		if(w.getName().equals("Hub")) e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent  e) {
		
		if (e.getHand() != EquipmentSlot.HAND) return;
		
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		
		if (!p.getInventory().getItemInMainHand().equals(wand())) return;
		if (!p.hasPermission("blobcatraz.special.portal.wand")) {
			p.sendMessage(Util.prefix + "Permission \u00a7cblobcatraz.special.portal.wand \u00a7rrequired!");
			return;
		}
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			Location loc = e.getClickedBlock().getLocation();
			if (primary.containsKey(uuid)) primary.remove(uuid);
			primary.put(uuid, loc);
			p.sendMessage(Util.prefix + "\u00a72Primary \u00a7rlocation set!");
			e.setCancelled(true);
		
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Location loc = e.getClickedBlock().getLocation();
			if (primary.get(uuid).getWorld() != loc.getWorld()) {
				e.getPlayer().sendMessage(Util.prefix + "The \u00a72Secondary \u00a7rlocation must be set in the same world as the \u00a72Primary \u00a7rlocation!");
				return;
			}
			
			if (secondary.containsKey(uuid)) secondary.remove(uuid);
			secondary.put(uuid, loc);
			p.sendMessage(Util.prefix + "\u00a72Secondary \u00a7rlocation set!");
			
		}
	}

	public static Location getPrimary(UUID uuid) {
		
		return primary.get(uuid);
	}
	
	public static Location getSecondary(UUID uuid) {
		
		return secondary.get(uuid);
	}
}
