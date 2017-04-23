package com.ToonBasic.blobcatraz.listener.sign;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenEnchantSign implements Listener {
	private static final String ENCHANT = Util.color("&2&l[Enchant]");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.enchant")) {
			String l0 = e.getLine(0).toLowerCase();
			if(l0.equals("[enchant]")) {
				String en = e.getLine(1);
				String name = en.toLowerCase();
				Enchantment ench = ItemUtil.getEnchant(name);
				if(ench == null) {
					String error = "Invalid Enchantment!";
					PlayerUtil.action(p, error);
					Block b = e.getBlock();
					b.breakNaturally();
				} else {
					String lvl = e.getLine(2);
					lvl = Util.onlyInteger(lvl);
					if(lvl.equals("")) lvl = "1";
					
					e.setLine(0, ENCHANT);
					e.setLine(1, name);
					e.setLine(2, lvl);
				}
			}
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
				String l0 = s.getLine(0);
				if(l0.equals(ENCHANT)) {
					String l1 = s.getLine(1);
					Enchantment ench = ItemUtil.getEnchant(l1);
					if(ench == null) b.breakNaturally();
					else {
						String lvl = s.getLine(2);
						int level = Integer.parseInt(lvl);
						double price = level * 5000.0D;
						ItemStack is = e.getItem();
						if(is != null) {
							ItemMeta meta = is.getItemMeta();
							if(meta.hasEnchant(ench)) {
								level = level + meta.getEnchantLevel(ench);
								if(level > 32767) level = 32767;
							}
							
							double bal = ConfigDatabase.balance(p);
							if(bal >= price) {
								ConfigDatabase.withdraw(p, price);
								meta.addEnchant(ench, level, true);
								is.setItemMeta(meta);
								String msg = "You bought an enchantment for " + Util.money(price);
								PlayerUtil.action(p, msg);
							} else {
								String error = "You don't have enough money!";
								PlayerUtil.action(p, error);
							}
						} else {
							String error = "You cannot enchant AIR!";
							PlayerUtil.action(p, error);
						}
					}
				}
			}
		}
	}
}