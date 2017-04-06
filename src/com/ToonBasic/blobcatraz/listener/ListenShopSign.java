package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

/*
 * Shop Sign Format:
 * "[sell/buy]"
 * <amount>
 * <price>
 * <material>:<data>
 */
public class ListenShopSign implements Listener {
	private static final String BUY = Util.color("&1&l[Buy]");
	private static final String SELL = Util.color("&4&l[Sell]");
	
	@EventHandler
	public void create(SignChangeEvent e) {
		String l0 = e.getLine(0);
		if(l0.contains("[buy]")) sign(e, BUY);
		else if(l0.contains("[sell]")) sign(e, SELL);
	}
	
	private void sign(SignChangeEvent e, String top) {
		try {
			String am = e.getLine(1);
			am = Util.onlyInteger(am);
			int amount = Integer.parseInt(am);
			if(amount > 64) am = Integer.toString(64);
			String pr = e.getLine(2);
			pr = '$' + Util.onlyDouble(pr);
			String ma = e.getLine(3);
			if(!ItemUtil.special().containsKey(ma)) {
				Material mat = Material.matchMaterial(ma);
				if(mat == null) {
					Block b = e.getBlock();
					b.breakNaturally();
					return;
				} else ma = mat.name();
			}

			e.setLine(0, top);
			e.setLine(1, am);
			e.setLine(2, pr);
			e.setLine(3, ma);
		} catch(Exception ex) {}
	}

	@EventHandler
	public void buySell(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign) {
				Sign s = (Sign) bs;
				String[] lines = s.getLines();
				String l0 = lines[0];
				String am = lines[1];
				am = Util.onlyInteger(am);
				String pr = lines[2];
				pr = Util.onlyDouble(pr);
				String ma = lines[3];
				if(l0.contains(BUY) || l0.contains(SELL)) {
					try {
						int amount = Integer.parseInt(am);
						if(amount > 64) amount = 64;
						if(amount < 1) amount = 1;
						double price = Double.parseDouble(pr);
						short data = 0;
						ItemStack is = null;
						String[] ma2 = ma.split(":");
						if(ma2.length > 1) {
							String id = ma2[0];
							data = Short.parseShort(ma2[1]);
							is = ItemUtil.getItem(id, amount, data);
						} else {
							is = ItemUtil.getItem(ma, amount, data);
						}

						if(l0.equals(BUY)) {
							double bal = ConfigDatabase.balance(p);
							if(bal < price) {
								String error = "You don't have enough money!";
								p.sendMessage(error);
							} else {
								int first = pi.firstEmpty();
								if(first == -1) {
									String error = "Your inventory is too full to buy this!";
									p.sendMessage(error);
								} else {
									pi.addItem(is);
									ConfigDatabase.withdraw(p, price);
									String buy = Util.color("You bought &a" + amount + "&f of &a" + is.getType().name() + "&f for &2$" + price);
									p.sendMessage(buy);
								}
							}
						} else if(l0.equals(SELL)){
							if(has(p, is)) {
								remove(p, is);
								ConfigDatabase.deposit(p, price);
								String sell = Util.color("You sold &a" + amount + "&f of &a" + is.getType().name() + "&f for &2$" + price);
								p.sendMessage(sell);
							} else {
								String error = "You don't have that item!";
								p.sendMessage(error);
							}
						} else return;				
					} catch(Exception ex) {
						p.sendMessage("Invalid Shop Sign!");
						p.sendMessage("Please contact an admin");
					}
				}
			}
		}
	}

	private boolean has(Player p, ItemStack is) {
		try {
			PlayerInventory pi = p.getInventory();
			ItemStack[] items = pi.getContents();
			for(ItemStack nis : items) {
				Material mat1 = is.getType();
				Material mat2 = nis.getType();
				short data1 = is.getDurability();
				short data2 = nis.getDurability();
				int amount1 = is.getAmount();
				int amount2 = nis.getAmount();

				boolean type = (mat1 == mat2);
				boolean data = (data1 == data2);
				boolean amount = (amount2 >= amount1);
				if(type && data && amount) return true;
			}
			return false;
		} catch(Exception ex) {return false;}
	}

	private void remove(Player p, ItemStack is) {
		try {
			PlayerInventory pi = p.getInventory();
			ItemStack[] items = pi.getContents();
			for(ItemStack nis : items) {
				Material mat1 = is.getType();
				Material mat2 = nis.getType();
				short data1 = is.getDurability();
				short data2 = nis.getDurability();
				int amount1 = is.getAmount();
				int amount2 = nis.getAmount();

				boolean type = (mat1 == mat2);
				boolean data = (data1 == data2);
				boolean amou = (amount2 >= amount1);
				if(type && data && amou) {
					nis.setAmount(amount2 - amount1);
					break;
				}
			}
		} catch(Exception ex) {}
	}
}