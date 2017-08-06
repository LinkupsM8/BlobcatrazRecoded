package com.SirBlobman.blobcatraz.listener.sign;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ListenRepairSign implements Listener {
	private static final String REPAIR = Util.color("&b&l[&1Repair&b&l]");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.repair")) {
			String l = e.getLine(0).toLowerCase();
			if(l.equals("[repair]")) {
				e.setLine(0, REPAIR);
				e.setLine(1, NumberUtil.money(100000));
				String msg = "Created repair sign!";
				PlayerUtil.action(p, msg);
			}
		}
	}
	
	@EventHandler
	public void click(SignClickEvent e) {
		String l0 = e.getLine(0);
		if(l0.equals(REPAIR)) {
			Player p = e.getPlayer();
			ItemStack is = PlayerUtil.held(p);
			if(ItemUtil.air(is)) {
				String error = "You cannot repair AIR!";
				PlayerUtil.action(p, error);
			} else {
				double price = 100000;
				double bal = ConfigDatabase.balance(p);
				if(bal >= price) {
					String name = ItemUtil.name(is);
					ConfigDatabase.withdraw(p, price);
					short dur = 0;
					is.setDurability(dur);
					String msg = Util.format("Successfully repaired your %1s", name);
					PlayerUtil.action(p, msg);
				} else {
					String error = "You don't have enough money!";
					p.sendMessage(error);
				}
			}
		}
	}
}