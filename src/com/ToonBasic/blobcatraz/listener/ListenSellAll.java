package com.ToonBasic.blobcatraz.listener;

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

import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenSellAll implements Listener {
	private static final String SELL_ALL = Util.color("&4&l[&1Sell All&4&l]&r");
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
	
	private void gui(Player p) {
		int size = 54;
		Inventory i = Bukkit.createInventory(null, size);
		p.openInventory(i);
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		HumanEntity he = e.getPlayer();
		if(he instanceof Player) {
			Player p = (Player) he;
			Inventory i = e.getInventory();
			if(i != null) {
				String title = i.getTitle();
				if(title.equals(TITLE)) {
					double worth = ItemUtil.getWorth(i);
				}
			}
		}
	}
}