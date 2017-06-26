package com.SirBlobman.blobcatraz.listener.sign;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ListenShopSign implements Listener {
	private static final String BUY = Util.color("&1&l[Buy]");
	private static final String TITLE = Util.color("&1&l&nBuying:&r ");
	private static final ItemStack FILLER = ItemUtil.newItem(Material.STAINED_GLASS_PANE, 1, 8, "&f");
	private static final ItemStack CANCEL = ItemUtil.newItem(Material.STAINED_GLASS_PANE, 1, 14, "&4&l\u2717 &cCancel &4&l\u2717");
	private static final ItemStack CONFIRM = ItemUtil.newItem(Material.STAINED_GLASS_PANE, 1, 5, "&2&l\u2713 &aConfirm &2&l\u2713");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.shop")) {
			String l0 = e.getLine(0).toLowerCase();
			if(l0.contains("[buy]")) {
				String l1 = e.getLine(1);
				String l2 = e.getLine(2);
				if(l2.equals("")) l2 = "0";
				l2 = Util.str(NumberUtil.getInteger(l2));
				try {
					short data = Short.parseShort(l2);
					ItemStack is = ItemUtil.getItem(l1, 1, data);
					if(ItemUtil.air(is)) {
						String error = Util.format("&cInvalid Item Name: &a%1s", l1);
						PlayerUtil.action(p, error);
						Block b = e.getBlock();
						b.breakNaturally();
					} else {
						e.setLine(0, BUY);
						e.setLine(1, l1);
						e.setLine(2, Util.str(data));
					}
				} catch(Throwable ex) {
					String error = "Invalid Shop Sign!";
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
		if(l0.equals(BUY)) {
			Player p = e.getPlayer();
			String l1 = e.getLine(1);
			String l2 = e.getLine(2);
			int data = NumberUtil.getInteger(l2);
			ItemStack is = ItemUtil.getItem(l1, 1, data);
			Inventory i = shop(is);
			p.openInventory(i);
			String msg = "Opening Shop...";
			PlayerUtil.action(p, msg);
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			InventoryView iv = e.getView();
			Inventory i = iv.getTopInventory();
			if(i != null) {
				String name = i.getName();
				if(name != null && name.startsWith(TITLE)) {
					e.setCancelled(true);
					ItemStack is = e.getCurrentItem();
					if(!ItemUtil.air(is)) {
						if(is.equals(CANCEL)) p.closeInventory();
						else if(is.equals(CONFIRM)) {
							ItemStack buy = i.getItem(4);
							ItemMeta meta = buy.getItemMeta();
							meta.setLore(Util.newList());
							int a = buy.getAmount();
							double price = ItemUtil.worth(buy) * 2;
							double bal = ConfigDatabase.balance(p);
							if(bal >= price) {
								PlayerInventory pi = p.getInventory();
								int slot = pi.firstEmpty();
								if(slot == -1) {
									String error = "Your inventory is full!";
									PlayerUtil.action(p, error);
								} else {
									ConfigDatabase.withdraw(p, price);
									pi.setItem(slot, buy);
									p.closeInventory();
									String msg = Util.format("&eYou bought &a%1s &eof &a%2s &efor &a%3s", a, ItemUtil.name(buy), NumberUtil.money(price));
									PlayerUtil.action(p, msg);
								}
							} else {
								p.closeInventory();
								String error = "You don't have enough money!";
								PlayerUtil.action(p, error);
							}
						} else if(is.equals(amount(-32))) recalc(i, -32);
						else if(is.equals(amount(-10))) recalc(i, -10);
						else if(is.equals(amount(-1))) recalc(i, -1);
						else if(is.equals(amount(1))) recalc(i, 1);
						else if(is.equals(amount(10))) recalc(i, 10);
						else if(is.equals(amount(32))) recalc(i, 32);
					}
				}
			}
		}
	}
	
	private void recalc(Inventory i, int amount) {
		ItemStack is = i.getItem(4);
		int amt = is.getAmount();
		int amnt = amt + amount;
		if(amnt < 1) amnt = 1;
		if(amnt > 64) amnt = 64;
		is.setAmount(amnt);
		i.setItem(4, withPrice(is));
	}
	
	private static ItemStack amount(int a) {
		boolean neg = (a < 0);
		short data = neg ? NumberUtil.toShort(3) : NumberUtil.toShort(4);
		String disp = neg ? ("&c" + a) : ("&e+" + a);
		ItemStack is = ItemUtil.newItem(Material.STAINED_GLASS_PANE, neg ? (-1 * a) : a, data, disp);
		return is;
	}
	
	private static ItemStack withPrice(ItemStack is) {
		ItemStack ib = is.clone();
		double price = ItemUtil.worth(is) * 2;
		String pr = NumberUtil.money(price);
		List<String> lore = Util.newList(pr);

		ItemMeta meta = ib.getItemMeta();
		meta.setLore(lore);
		ib.setItemMeta(meta);
		return ib;
	}
	
	private static Inventory filler(ItemStack is) {
		Inventory i = Bukkit.createInventory(null, 54, TITLE + ItemUtil.name(is));
		for(int j = 0; j < 54; j++) {i.setItem(j, FILLER);}
		int[] con = new int[] {36, 37, 38, 45, 46, 47};
		int[] can = new int[] {42, 43, 44, 51, 52, 53};
		for(int j : con) {i.setItem(j, CONFIRM);}
		for(int j : can) {i.setItem(j, CANCEL);}
		i.setItem(4, is);
		i.setItem(19, amount(-32));
		i.setItem(20, amount(-10));
		i.setItem(21, amount(-1));
		i.setItem(23, amount(1));
		i.setItem(24, amount(10));
		i.setItem(25, amount(32));
		return i;
	}
	
	public static Inventory shop(ItemStack is) {
		ItemStack ib = withPrice(is);
		Inventory i = filler(ib);
		return i;
	}
}