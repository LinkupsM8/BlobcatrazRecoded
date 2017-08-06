package com.SirBlobman.blobcatraz.listener.sign;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenEnchantSign implements Listener {
	private static final String ENCHANT = Util.color("&1&l[Enchant]");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		String perm = "blobcatraz.sign.enchant";
		if(p.hasPermission(perm)) {
			String[] lines = e.getLines();
			String l0 = lines[0].toLowerCase();
			if(l0.equals("[enchant]")) {
				String en = lines[1];
				String name = en.toLowerCase();
				Enchantment ench = ItemUtil.getEnchant(name);
				if(ench != null) {
					String lvl = lines[2];
					int level = NumberUtil.getInteger(lvl);
					lvl = Util.str(level);
					if(lvl.equals("")) lvl = "1";
					
					e.setLine(0, ENCHANT);
					e.setLine(1, name);
					e.setLine(2, lvl);
				} else {
					String error = Util.format(Util.PREFIX + "Invalid Enchantment: '%1s'", en);
					PlayerUtil.action(p, error);
					Block b = e.getBlock();
					b.breakNaturally();
				}
			}
		}
	}
	
	@EventHandler
	public void click(SignClickEvent e) {
		String l0 = e.getLine(0);
		String l1 = e.getLine(1);
		String l2 = e.getLine(2);
		
		if(l0.equals(ENCHANT)) {
			Enchantment ench = ItemUtil.getEnchant(l1);
			if(ench == null) {
				Block b = e.getBlock();
				b.breakNaturally();
			} else{
				Player p = e.getPlayer();
				ItemStack is = PlayerUtil.held(p);
				if(!ItemUtil.air(is)) {
					int level = NumberUtil.getInteger(l2);
					ItemMeta meta = is.getItemMeta();
					if(meta.hasEnchant(ench)) {
						int lvl = meta.getEnchantLevel(ench);
						level += lvl;
						if(level > 32767) level = 32767;
					}
					
					double price = level * 5000;
					double bal = ConfigDatabase.balance(p);
					if(bal >= price) {
						ConfigDatabase.withdraw(p, price);
						meta.addEnchant(ench, level, true);
						is.setItemMeta(meta);
						String msg = "You bought an enchantment for " + NumberUtil.money(price);
						p.sendMessage(msg);
					} else {
						String error = "You don't have enough money!";
						PlayerUtil.action(p, error);
					}
				} else {
					String error = "You cannot enchant AIR!";
					p.sendMessage(error);
				}
			}
		}
	}
}