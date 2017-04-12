package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.command.staff.CommandVanish.vanished;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandStaff extends ICommand {
    public CommandStaff() {super("staff", "", "blobcatraz.player.staff", "admins", "liststaff");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        PlayerUtil.action(p, "Opening the staff GUI...");
        gui(p);
    }
    
    public static void gui(Player p) {
    	Inventory i = Bukkit.createInventory(null, 9, Util.color("&2Online Staff"));
    	ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, Util.intToShort(3));
    	ItemMeta meta = head.getItemMeta();
    	SkullMeta sm = (SkullMeta) meta;
    	
    	int j = 0;
    	String perm = "blobcatraz.staff.staff";
    	for(Player o : Bukkit.getOnlinePlayers()) {
    		if(o.hasPermission(perm)) {
    			if(!vanished.contains(p)) {
    				String name = o.getName();
    				String dname = Util.color("&5" + name);
    				sm.setOwner(name);
    				sm.setDisplayName(dname);
    				head.setItemMeta(sm);
    				i.setItem(j, head);
    				j++;
    			}
    		}
    	}
    	p.openInventory(i);
    }
}
