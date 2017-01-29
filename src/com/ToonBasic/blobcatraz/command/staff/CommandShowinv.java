package com.ToonBasic.blobcatraz.command.staff;

import java.util.ArrayList;
import java.util.Arrays;

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

@PlayerOnly
public class CommandShowinv extends ICommand implements Listener {
	
    public CommandShowinv() {
        super("showinv", "<player> [armor]", "blobcatraz.staff.showinv", "invsee");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
    	
    	Player p = (Player) cs;

		if (args.length == 1) {

			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			PlayerInventory p2Inv = p2.getInventory();
			
			Inventory inv = Bukkit.createInventory(null, 36, "Inventory of " + p2.getName());
			for (int i = 27; i < 36; i++) {
				inv.setItem(i, p2Inv.getItem(i - 27));
			} for (int i = 0; i < 27; i++) {
				inv.setItem(i, p2Inv.getItem(i + 9));
			}
			
			p.openInventory(inv);
			p.sendMessage("You have been shown the inventory of " + p2.getName());

		} else if (args.length > 1 && args[1].equalsIgnoreCase("armor")) {

			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			PlayerInventory p2Inv = p.getInventory();
			
			Inventory inv = Bukkit.createInventory(null, 9, "Armor of " + p.getName());
			inv.setItem(0, p2Inv.getHelmet());
			inv.setItem(1, new Separator("§7◄ §lHelmet", "§8§lChestplate §r§8►"));
			inv.setItem(2, p2Inv.getChestplate());
			inv.setItem(3, new Separator("§7◄ §lChestplate", "§8§lLeggings §r§8►"));
			inv.setItem(4, p2Inv.getLeggings());
			inv.setItem(5, new Separator("§7◄ §lLeggings", "§8§lBoots §r§8►"));
			inv.setItem(6, p2Inv.getBoots());
			inv.setItem(7, new Separator("§7◄ §lBoots", "§8§lShield §r§8►"));
			inv.setItem(8, p2Inv.getItemInOffHand());
			
			p.openInventory(inv);
			p.sendMessage("You have been shown the armor of " + p2.getName());

		} else p.sendMessage("Missing Arguments: Did you mean /showinv [player] [armor]");
    	
    }
    
    @EventHandler
	private void onInventoryClicked(InventoryClickEvent e) {

		Inventory inv = e.getInventory();
		String invName = inv.getName();

		if (invName.startsWith("Inventory of ")) {
			
			// Some logic.
			
		} else if (invName.startsWith("Armor of ")) {

			if (e.getRawSlot() % 2 != 0 && e.getRawSlot() > -1 && e.getRawSlot() < 8) e.setCancelled(true);
		}

	}
	
	@EventHandler
	private void onInventoryClosed(InventoryCloseEvent e) {

		Inventory inv = e.getInventory();
		String invName = inv.getName();

		if (invName.startsWith("Inventory of ")) {
			
			PlayerInventory p2Inv = Bukkit.getServer().getPlayer(invName.substring(13)).getInventory();
			
			for (int i = 0; i < 9; i++) {
				p2Inv.setItem(i, inv.getItem(i + 27));
			} for (int i = 9; i < 36; i++) {
				p2Inv.setItem(i, inv.getItem(i - 9));
			}
			
		} else if (invName.startsWith("Armor of ")) {

			PlayerInventory p2Inv = Bukkit.getServer().getPlayer(invName.substring(9)).getInventory();

			p2Inv.setHelmet(inv.getItem(0));
			p2Inv.setChestplate(inv.getItem(2));
			p2Inv.setLeggings(inv.getItem(4));
			p2Inv.setBoots(inv.getItem(6));
			p2Inv.setItemInOffHand(inv.getItem(8));

		}

	}
	
	private class Separator extends ItemStack {
		
		public Separator(String left, String right) {
			
			super(Material.IRON_FENCE, 1);
			ItemMeta meta = getItemMeta();
			meta.setDisplayName(left);
			meta.setLore(new ArrayList<String>(Arrays.asList("     " + right)));
			setItemMeta(meta);
			
		}
		
	}
    
}
