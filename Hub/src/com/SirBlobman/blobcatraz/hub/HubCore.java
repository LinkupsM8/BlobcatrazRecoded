package com.SirBlobman.blobcatraz.hub;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class HubCore extends Core implements Listener {
	@Override
	public void onEnable() {
	    Plugin plugin = this;
	    Util.regEvents(plugin, this);
	    if(Util.PM.isPluginEnabled("ProtocolSupport")) Util.regEvents(plugin, new BlockVersion());
	}
	
	@Override
	public void onDisable() {}
	
	private static final String TITLE = Util.color("&3&oBlobcatraz");
	private static final ItemStack COMPASS = ItemUtil.newItem(Material.COMPASS, 1, 0, TITLE, "&7&oClick to get started...");
	private static final ItemStack SKYBLOCK = ItemUtil.newItem(Material.SAPLING, 1, 0, "&2&lSkyBlock");
	private static final ItemStack LOBBY_SELECT = ItemUtil.newItem(Material.EYE_OF_ENDER, 1, 0, "&3Lobby Selector");
	private static final ItemStack PRISON = ItemUtil.newItem(Material.IRON_FENCE, 1, 0, "&7&lPrison");

	@EventHandler(priority=EventPriority.HIGHEST)
	private void inv(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		World w = p.getWorld();
		String name = w.getName();
		if(name.equals("Spawn")) {
			PlayerInventory pi = p.getInventory();
			pi.clear();
			pi.addItem(COMPASS);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void inv(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		World w = p.getWorld();
		String name = w.getName();
		if(name.equals("Spawn")) {
			PlayerInventory pi = p.getInventory();
			pi.clear();
			pi.addItem(COMPASS);
		}
	}
	
	@EventHandler
	private void compass(PlayerInteractEvent e) {
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			ItemStack is = e.getItem();
			if(!ItemUtil.air(is)) {
				if(is.equals(COMPASS)) {
					e.setCancelled(true);
					Player p = e.getPlayer();
					Inventory i = gui();
					p.openInventory(i);
				}
			}
		}
	}
	
	@EventHandler
	private void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			World w = p.getWorld();
			String name = w.getName();
			if(name.equals("Spawn")) {
				GameMode gm = p.getGameMode();
				if(gm != GameMode.CREATIVE) e.setCancelled(true);
				InventoryView iv = e.getView();
				Inventory i = iv.getTopInventory();
				if(i != null) {
					String title = i.getName();
					if(title != null && title.equals(TITLE)) {
						e.setCancelled(true);
						ItemStack is = e.getCurrentItem();
						if(!ItemUtil.air(is)) {
							if(is.equals(SKYBLOCK)) {
								String cmd = "island";
								p.performCommand(cmd);
								p.closeInventory();
							} else if(is.equals(LOBBY_SELECT)) {
								String[] msg = Util.color(
									"&7&m==================================================",
									"&fSorry, this feature is currently unavailable",
									"&7&m=================================================="
								);
								p.sendMessage(msg);
								p.closeInventory();
							} else if(is.equals(PRISON)) {
								String cmd = "warp A";
								p.performCommand(cmd);
								p.closeInventory();
							}
						}
					}
				}
			}
		}
	}
	
	private Inventory gui() {
		Inventory i = Bukkit.createInventory(null, 9, TITLE);
		i.setItem(2, SKYBLOCK);
		i.setItem(4, LOBBY_SELECT);
		i.setItem(6, PRISON);
		return i;
	}
}