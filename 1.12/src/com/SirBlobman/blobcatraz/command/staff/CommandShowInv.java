package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandShowInv extends PlayerCommand implements Listener {
	private static final char LTRI = '\u25c4';
	private static final char RTRI = '\u25b6';
	public CommandShowInv() {super("showinv", "<player> [armor]", "blobcatraz.staff.showinv", "invsee", "openinv");}
	
	@Override
	public void run(Player p, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String name = t.getName();
			PlayerInventory ti = t.getInventory();
			if(args.length > 1) {
				String sub = args[1].toLowerCase();
				if(sub.equals("armor")) {
					ItemStack helm = ti.getHelmet();
					ItemStack ches = ti.getChestplate();
					ItemStack pant = ti.getLeggings();
					ItemStack boot = ti.getLeggings();
					ItemStack offh = ti.getItemInOffHand();
					
					Inventory i = Bukkit.createInventory(null, 9, "Armor of " + name);
					i.setItem(0, helm);
					i.setItem(1, separator("&7" + LTRI + " &lHelmet", "&8&lChestplate &8" + RTRI));
					i.setItem(2, ches);
					i.setItem(3, separator("&7" + LTRI + " &lChestplate", "&8&lLeggings &8" + RTRI));
					i.setItem(4, pant);
					i.setItem(5, separator("&7" + LTRI + " &lLeggings", "&8&lBoots &8" + RTRI));
					i.setItem(6, boot);
					i.setItem(7, separator("&7" + LTRI + " &lBoots", "&8&lOff Hand &8" + RTRI));
					i.setItem(8, offh);
					p.openInventory(i);
					String msg = prefix + "You are being shown the armor of " + name;
					p.sendMessage(msg);
				} else {
					String error = getFormattedUsage(getCommandUsed());
					p.sendMessage(error);
				}
			} else {
				p.openInventory(ti);
				String msg = prefix + "You are being shown the inventory of " + name;
				p.sendMessage(msg);
			}
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		String name = i.getName();
		if(name.startsWith("Armor of ")) {
			int raw = e.getRawSlot();
			if(((raw % 2) != 0) && (raw > -1) && (raw < 8)) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		Inventory i = e.getInventory();
		String name = i.getName();
		if(name.startsWith("Armor of ")) {
			String target = name.substring(9);
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				PlayerInventory ti = t.getInventory();
				
				ItemStack h = i.getItem(0), c = i.getItem(2),
				l = i.getItem(4), b = i.getItem(6),
				o = i.getItem(8);
				
				ti.setHelmet(h); ti.setChestplate(c);
				ti.setLeggings(l); ti.setBoots(b);
				ti.setItemInOffHand(o);
			}
		}
	}
	
	private ItemStack separator(String l, String r) {
		String disp = Util.color(l);
		String lore = Util.color("     " + r);
		ItemStack is = ItemUtil.newItem(Material.IRON_FENCE, 1, 1, disp, lore);
		return is;
	}
}