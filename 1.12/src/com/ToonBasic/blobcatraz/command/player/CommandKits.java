package com.ToonBasic.blobcatraz.command.player;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandKits extends ICommand implements Listener {
	private static final String TITLE = Util.color("&1Kits");
	private static Map<Player, Integer> pages = Util.newMap();
	public CommandKits() {super("kits", "[page]", "blobcatraz.player.kit");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		int page = 1;
		if(args.length > 0) {
			String pg = args[0];
			page = NumberUtil.getInteger(pg);
			if(page < 1) page = 1;
		}
		Inventory gui = gui(p, page);
		p.openInventory(gui);
		pages.put(p, page);
		p.sendMessage(prefix + "Opening Kits GUI...");
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			InventoryView iv = e.getView();
			Inventory i = iv.getTopInventory();
			if(i != null) {
				String title = i.getName();
				if(title.equals(TITLE)) {
					e.setCancelled(true);
					ItemStack is = e.getCurrentItem();
					if(is != null) {
						if(is.equals(back())) {
							int page = pages.get(p);
							page -= 1;
							if(page < 1) page = 1;
							String cmd = "kits " + page;
							p.closeInventory();
							p.performCommand(cmd);
							pages.put(p, page);
						} else if(is.equals(next())) {
							int page = pages.get(p);
							page += 1;
							String cmd = "kits " + page;
							p.closeInventory();
							p.performCommand(cmd);
						} else {
							ItemMeta meta = is.getItemMeta();
							String disp = meta.getDisplayName();
							String name = Util.strip(disp);
							String cmd = "kit " + name;
							p.closeInventory();
							p.performCommand(cmd);
						}
					}
				}
			}
		}
	}
	
	private final ItemStack back() {
		ItemStack back = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta meta = back.getItemMeta();
		meta.setDisplayName(Util.color("&f&lPrevious Page"));
		back.setItemMeta(meta);
		return back;
	}
	
	private final ItemStack next() {
		ItemStack next = new ItemStack(Material.FEATHER);
		ItemMeta meta = next.getItemMeta();
		meta.setDisplayName(Util.color("&f&lNext Page"));
		next.setItemMeta(meta);
		return next;	
	}
	
	private Inventory gui(Player p, int page) {
		List<String> list = ConfigKits.kits();
		int start = (27 * page) - 27;
		int end = start + 27;
		Inventory i = Bukkit.createInventory(null, 45, TITLE);
		for(int j = start; j < end; j++) {
			if(list.size() > j) {
				String name = list.get(j);
				if(p.hasPermission("blobcatraz.kits." + name)) {
					List<ItemStack> kit = ConfigKits.kit(name);
					ItemStack icon = kit.get(0);
					String disp = Util.color("&2" + name);
					ItemMeta meta = icon.getItemMeta();
					meta.setDisplayName(disp);
					icon.setItemMeta(meta);
					i.setItem(j, icon);
				}
			} else break;
		}
		i.setItem(36, back());
		i.setItem(44, next());
		return i;
	}
}