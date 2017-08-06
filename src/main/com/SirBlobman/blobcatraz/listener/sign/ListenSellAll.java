package com.SirBlobman.blobcatraz.listener.sign;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ListenSellAll implements Listener {
	private static final String SELL_ALL = Util.color("&4[&1Sell All&4]");
	private static final String TITLE = Util.color("&4&lSell All");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.sellall")) {
			String l = e.getLine(0).toLowerCase();
			if(l.equals("[sell all]")) {
				e.setLine(0, SELL_ALL);
				String msg = "Successfully created a sellall sign";
				PlayerUtil.action(p, msg);
			}
		}
	}
	
	@EventHandler
	public void click(SignClickEvent e) {
		String l = e.getLine(0);
		if(l.equals(SELL_ALL)) {
			Player p = e.getPlayer();
			gui(p);
			String msg = "Opening Sell All GUI...";
			PlayerUtil.action(p, msg);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		HumanEntity he = e.getPlayer();
		if(he instanceof Player) {
			Player p = (Player) he;
			InventoryView iv = e.getView();
			Inventory i = iv.getTopInventory();
			if(i != null) {
				String name = i.getTitle();
				if(name.equals(TITLE)) {
					ItemStack[] inv = i.getContents();
					double worth = ItemUtil.worth(inv);
					ConfigDatabase.deposit(p, worth);
					String price = NumberUtil.money(worth);
					String msg = Util.format(Util.PREFIX + "You sold an inventory worth %1s", price);
					p.sendMessage(msg);
				}
			}
		}
	}
	
	public static void gui(Player p) {
		Inventory i = Bukkit.createInventory(null, 54, TITLE);
		p.openInventory(i);
	}
}