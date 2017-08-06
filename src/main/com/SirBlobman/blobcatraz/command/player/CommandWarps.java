package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.config.special.Warp;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class CommandWarps extends PlayerCommand implements Listener {
	private static final String TITLE = Util.color("&1Warps");
	private static final ItemStack NEXT = ItemUtil.newItem(Material.FEATHER, 1, 0, "&fNext Page");
	private static final ItemStack BACK = ItemUtil.newItem(Material.ARROW, 1, 0, "&fPrevious Page");
	private static Map<Player, Integer> PAGE = Util.newMap();
	public CommandWarps() {super("warps", "[page]", "blobcatraz.player.warp");}
	
	@Override
	public void run(Player p, String[] args) {
		int page = 1;
		if(args.length > 0) {
			page = NumberUtil.getInteger(args[0]);
			if(page < 1) page = 1;
		}
		
		Inventory i = gui(p, page);
		p.openInventory(i);
		PAGE.put(p, page);
		String msg = prefix + "Opening Warps GUI...";
		p.sendMessage(msg);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			Inventory i = e.getClickedInventory();
			if(i != null) {
				String title = i.getName();
				if(title != null && title.equals(TITLE)) {
					e.setCancelled(true);
					ItemStack is = e.getCurrentItem();
					if(!ItemUtil.air(is)) {
						if(is.equals(BACK)) {
							int page = PAGE.get(p);
							page -= 1;
							if(page < 1) page = 1;
							String cmd = Util.format("warps %1s", page);
							p.closeInventory();
							p.performCommand(cmd);
							PAGE.put(p, page);
						} else if(is.equals(NEXT)) {
							int page = PAGE.get(p);
							page += 1;
							String cmd = Util.format("warps %1s", page);
							p.closeInventory();
							p.performCommand(cmd);
							PAGE.put(p, page);
						} else {
							String name = ItemUtil.name(is);
							String s = Util.strip(name);
							String cmd = Util.format("warp %1s", s);
							p.closeInventory();
							p.performCommand(cmd);
						}
					}
				}
			}
		}
	}
	
	private Inventory gui(Player p, int page) {
		List<Warp> warps = ConfigWarps.warps();
		int end = (page * 27);
		int start = end - 27;
		Inventory i = blank(page, end);
		if(warps.isEmpty()) return i;
		else {
			if(warps.size() < start) return gui(p, 1);
			if(warps.size() < end) end = warps.size();
			List<Warp> list = warps.subList(start, end);
			int j = 0;
			for(Warp w : list) {
				String s = w.name();
				String name = Util.format("&2%1s", s);
				String perm = Util.format("blobcatraz.warps.%1s", s);
				if(p.hasPermission(perm)) {
					ItemStack is = w.icon().clone();
					is.setAmount(1);
					ItemMeta meta = is.getItemMeta();
					meta.addItemFlags(ItemFlag.values());
					meta.setDisplayName(name);
					is.setItemMeta(meta);
					i.setItem(j, is);
					j++;
				} else continue;
			}
			return i;
		}
	}
	
	private Inventory blank(int page, int end) {
		List<Warp> list = ConfigWarps.warps();
		Inventory i = Bukkit.createInventory(null, 45, TITLE);
		if(page != 1) i.setItem(36, BACK);
		if(list.size() > end) i.setItem(44, NEXT);
		return i;
	}
}