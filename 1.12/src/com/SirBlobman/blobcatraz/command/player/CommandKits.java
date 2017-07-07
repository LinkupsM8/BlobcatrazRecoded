package com.SirBlobman.blobcatraz.command.player;

import java.util.List;
import java.util.Map;

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

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandKits extends PlayerCommand implements Listener {
	private static final String TITLE = Util.color("&1Kits");
	private static final ItemStack NEXT = ItemUtil.newItem(Material.FEATHER, 1, 0, "&fNext Page");
	private static final ItemStack BACK = ItemUtil.newItem(Material.ARROW, 1, 0, "&fPrevious Page");
	private static Map<Player, Integer> PAGE = Util.newMap();
	public CommandKits() {super("kits", "[page]", "blobcatraz.player.kit");}
	
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
		String msg = prefix + "Opening Kits GUI...";
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
							String cmd = Util.format("kits %1s", page);
							p.closeInventory();
							p.performCommand(cmd);
							PAGE.put(p, page);
						} else if(is.equals(NEXT)) {
							int page = PAGE.get(p);
							page += 1;
							String cmd = Util.format("kits %1s", page);
							p.closeInventory();
							p.performCommand(cmd);
							PAGE.put(p, page);
						} else {
							String name = ItemUtil.name(is);
							String s = Util.strip(name);
							String cmd = Util.format("kit %1s", s);
							p.closeInventory();
							p.performCommand(cmd);
						}
					}
				}
			}
		}
	}
	
	private Inventory gui(Player p, int page) {
		List<String> list = ConfigKits.kits();
		int start = (page * 27) - 27;
		Inventory i = Bukkit.createInventory(null, 45, TITLE);
		for(int j = 0; j < 27; j++) {
			int in = (j + start);
			if(list.size() > in) {
				String name = list.get(in);
				String perm = Util.format("blobcatraz.kits.%1s", name);
				if(p.hasPermission(perm)) {
					ItemStack is = ConfigKits.icon(name);
					String disp = Util.color("&2" + name);
					ItemMeta meta = is.getItemMeta();
					meta.setDisplayName(disp);
					meta.addItemFlags(ItemFlag.values());
					is.setItemMeta(meta);
					i.setItem(j, is);
				} else continue;
			} else break;
		}
		
		i.setItem(36, BACK);
		i.setItem(44, NEXT);
		return i;
	}
}