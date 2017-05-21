package com.ToonBasic.blobcatraz.listener.sign;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenSellAll implements Listener {
	private static final String SELL_ALL = Util.color("&4[&1Sell All&4]");
	private static final String TITLE = Util.color("&4&lSell All");
	
	@EventHandler
	public void place(SignChangeEvent e) {
		Player p = e.getPlayer();
		String line = e.getLine(0);
		if(line.contains("[sell all]")) {
			e.setLine(0, SELL_ALL);
			PlayerUtil.action(p, "Created " + SELL_ALL + " sign");
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign) {
				Sign s = (Sign) bs;
				String line = s.getLine(0);
				if(line.equals(SELL_ALL)) {
					gui(p);
					PlayerUtil.action(p, "Opening Sell All GUI");
				}
			}
		}
	}
	
	public static void gui(Player p) {
		int size = 54;
		Inventory i = Bukkit.createInventory(null, size, TITLE);
		p.openInventory(i);
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		HumanEntity he = e.getPlayer();
		if(he instanceof Player) {
			Player p = (Player) he;
			InventoryView iv = e.getView();
			Inventory i = iv.getTopInventory();
			if(i != null) {
				String title = i.getTitle();
				if(title.equals(TITLE)) {
					ItemStack[] items = i.getContents();
					double worth = ItemUtil.worth(items);
					ConfigDatabase.deposit(p, worth);
					p.sendMessage(Util.prefix + "You sold an inventory worth " + NumberUtil.money(worth));
				}
			}
		}
	}
}