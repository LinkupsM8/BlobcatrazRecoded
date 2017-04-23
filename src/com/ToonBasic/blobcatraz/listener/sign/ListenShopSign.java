package com.ToonBasic.blobcatraz.listener.sign;

import static com.ToonBasic.blobcatraz.utility.ItemUtil.worth;
import static com.ToonBasic.blobcatraz.utility.Util.toShort;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

/*
 * Shop Sign Format:
 * "[Buy]"
 * <item name>
 * <data>
 */
public class ListenShopSign implements Listener {
	private static final String BUY = Util.color("&1&l[Buy]");
	private static final String TITLE = Util.color("&1&l&nBuying:&r ");	
	
	@EventHandler
	public void create(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.shop")) {
			String l0 = e.getLine(0).toLowerCase();
			if(l0.contains("[buy]")) sign(e);
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
				String top = s.getLine(0);
				String id = s.getLine(1);
				String l2 = s.getLine(2);
				if(l2.equals("")) l2 = "0";
				if(top.equals(BUY)) {
					short data = Short.parseShort(l2);
					ItemStack is = ItemUtil.getItem(id, 1, data);
					Inventory shop = shop(is);
					p.openInventory(shop);
					PlayerUtil.action(p, "Opening Shop...");
				}
			}
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			InventoryView iv = e.getView();
			Inventory i = iv.getTopInventory();
			String title = i.getTitle();
			if(title.startsWith(TITLE)) {
				e.setCancelled(true);
				ItemStack is = e.getCurrentItem();
				if(is != null) {
					if(is.equals(cancel())) p.closeInventory();
					if(is.equals(confirm())) {
						ItemStack is2 = i.getItem(4);
						ItemMeta meta = is2.getItemMeta();
						meta.setLore(Util.newList());
						is2.setItemMeta(meta);
						int amount = is2.getAmount();
						double price = worth(is) * 2;
						double bal = ConfigDatabase.balance(p);
						if(bal >= price) {
							PlayerInventory pi = p.getInventory();
							int slot = pi.firstEmpty();
							if(slot == -1) {
								String error = "Your inventory is full!";
								PlayerUtil.action(p, error);
							} else {
								ConfigDatabase.withdraw(p, price);
								pi.setItem(slot, is2);
								String msg = Util.color("&eYou bought &a" + amount + " &eof &a" + ItemUtil.name(is2));
								p.closeInventory();
								PlayerUtil.action(p, msg);
								PlayerUtil.ping(p);
							}
						} else {
							String error = "You don't have enough money!";
							p.closeInventory();
							PlayerUtil.action(p, error);
							PlayerUtil.ping(p);
						}
					}
					if(is.equals(amount(-64))) recalc(i, -64);
					if(is.equals(amount(-10))) recalc(i, -10);
					if(is.equals(amount(-1))) recalc(i, -1);
					if(is.equals(amount(1))) recalc(i, 1);
					if(is.equals(amount(10))) recalc(i, 10);
					if(is.equals(amount(64))) recalc(i, 64);
				}
			}
		}
	}
	
	private void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		String l1 = e.getLine(1);
		String l2 = e.getLine(2);
		if(l2.equals("")) l2 = "0";
		l2 = Util.onlyInteger(l2);
		try {
			if(!ItemUtil.special().containsKey(l1)) {
				Material mat = Material.matchMaterial(l1.toUpperCase());
				short data = Short.valueOf(l2);
				if(mat == null) {
					Block b = e.getBlock();
					b.breakNaturally();
					String error = "&cInvalid Item Name: &a" + l1;
					PlayerUtil.action(p, error);
					return;
				} else {
					e.setLine(0, BUY);
					e.setLine(1, mat.name());
					e.setLine(2, Short.toString(data));
				}
			} else {
				e.setLine(0, BUY);
				e.setLine(1, l1);
				e.setLine(2, "");
			}
		} catch(Exception ex) {
			String error = "Invalid Shop Sign!";
			PlayerUtil.action(p, error);
		}
	}
	
	private Inventory shop(ItemStack toBuy) {
		ItemStack copy = withPrice(toBuy);
		Inventory i = fillerInv(toBuy);
		i.setItem(4, copy);
		i.setItem(19, amount(-64));
		i.setItem(20, amount(-10));
		i.setItem(21, amount(-1));
		i.setItem(23, amount(1));
		i.setItem(24, amount(10));
		i.setItem(25, amount(64));
		return i;
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
	
	private Inventory fillerInv(ItemStack is) {
		Inventory i = Bukkit.createInventory(null, 54, TITLE + ItemUtil.name(is));
		for(int j = 0; j < 54; j++) {i.setItem(j, filler());}
		int[] con = new int[] {36, 37, 38, 45, 46, 47};
		int[] can = new int[] {42, 43, 44, 51, 52, 53};
		for(int j : con) {i.setItem(j, confirm());}
		for(int j : can) {i.setItem(j, cancel());}
		return i;
	}
	
	private final ItemStack filler() {
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, toShort(8));
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Util.color("&f"));
		is.setItemMeta(meta);
		return is;
	}
	
	private final ItemStack confirm() {
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, toShort(5));
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Util.color("&2\u2713 &aConfirm &2\u2713"));
		is.setItemMeta(meta);
		return is;
	}
	
	private final ItemStack cancel() {
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, toShort(14));
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Util.color("&4\u2717 &cCancel &4\u2717"));
		is.setItemMeta(meta);
		return is;
	}
	
	private final ItemStack amount(int change) {
		boolean neg = (change < 0);
		short data = neg ? toShort(3) : toShort(4);
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, neg ? (change * -1) : change, data);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(neg ? Util.color("&c" + change) : Util.color("&e+" + change));
		is.setItemMeta(meta);
		return is;
	}
	
	private ItemStack withPrice(ItemStack item) {
		ItemStack is = item.clone();
		ItemMeta meta = is.getItemMeta();
		double pr = worth(is) * 2;
		String price = Util.money(pr);
		meta.setLore(Util.newList(price));
		is.setItemMeta(meta);
		return is;
	}
}