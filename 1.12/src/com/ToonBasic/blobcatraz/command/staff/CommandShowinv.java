package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandShowinv extends ICommand implements Listener {
    private static final char LTRI = '\u25c4';
    private static final char RTRI = '\u25b6';
	public CommandShowinv() {super("showinv", "<player> [armor]", "blobcatraz.staff.showinv", "invsee", "openinv");}
    
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
    	Player p = (Player) cs;
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			PlayerInventory ti = t.getInventory();
	    	if(args.length > 1) {
	    		String sub = args[1].toLowerCase();
	    		if(sub.equals("armor")) {
	    			ItemStack helme = ti.getHelmet();
	    			ItemStack chest = ti.getChestplate();
	    			ItemStack pants = ti.getLeggings();
	    			ItemStack boots = ti.getBoots();
	    			ItemStack off = ti.getItemInOffHand();
	    			
	    			Inventory i = Bukkit.createInventory(null, 9, "Armor of " + t.getName());
	    			i.setItem(0, helme);
	    			i.setItem(1, new Separator("&7" + LTRI + " &lHelmet", "&8&lChestplate &8" + RTRI));
	    			i.setItem(2, chest);
	    			i.setItem(3, new Separator("&7" + LTRI + " &lChestplate", "&8&lLeggings &8" + RTRI));
	    			i.setItem(4, pants);
	    			i.setItem(5, new Separator("&7" + LTRI + " &lLeggings", "&8&lBoots &8" + RTRI));
	    			i.setItem(6, boots);
	    			i.setItem(7, new Separator("&7" + LTRI + " &lBoots", "&8&lOff Hand &8" + RTRI));
	    			i.setItem(8, off);
	    			p.openInventory(i);
	    			String msg = "You are being shown the armor of " + t.getName();
	    			p.sendMessage(msg);
	    		} else {
	    			String error = prefix + "Invalid Arguments: Did you mean /showinv <player> [armor]";
	    			p.sendMessage(error);
	    		}
	    	} else {
	    		p.openInventory(ti);
	    		p.sendMessage("You have been shown the inventory of " + t.getName());
	    	}
		} else {
			String error = prefix + Language.INVALID_TARGET;
			p.sendMessage(error);
		}
    }
    
    @EventHandler
	private void click(InventoryClickEvent e) {
    	Inventory i = e.getInventory();
    	String name = i.getName();
    	if(name.startsWith("Armor of ")) {
    		int raw = e.getRawSlot();
    		if(((raw % 2) != 0) && (raw > -1) && (raw < 8)) e.setCancelled(true);
    	}
    }
	
	@EventHandler
	private void close(InventoryCloseEvent e) {
		Inventory i = e.getInventory();
		String name = i.getName();
		if(name.startsWith("Armor of ")) {
			String target = name.substring(9);
			Player t = Bukkit.getPlayer(target);
			PlayerInventory ti = t.getInventory();
			
			ItemStack h = i.getItem(0);
			ItemStack c = i.getItem(2);
			ItemStack l = i.getItem(4);
			ItemStack b = i.getItem(6);
			ItemStack o = i.getItem(8);
			
			ti.setHelmet(h);
			ti.setChestplate(c);
			ti.setLeggings(l);
			ti.setBoots(b);
			ti.setItemInOffHand(o);
		}
	}
	
	private class Separator extends ItemStack {	
		public Separator(String left, String right) {	
			super(Material.IRON_FENCE, 1);
			left = Util.color(left);
			right = Util.color(right);
			ItemMeta meta = getItemMeta();
			meta.setDisplayName(left);
			List<String> lore = Util.newList("     " + right);
			meta.setLore(lore);
			setItemMeta(meta);	
		}	
	}
}