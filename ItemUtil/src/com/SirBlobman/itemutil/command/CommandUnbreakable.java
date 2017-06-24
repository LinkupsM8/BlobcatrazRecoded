package com.SirBlobman.itemutil.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandUnbreakable extends PlayerCommand {
	public CommandUnbreakable() {super("unbreakable", "", "blobcatraz.special.unbreakable", "unbreaking");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held != null && held.getType() != Material.AIR) {
			ItemMeta meta = held.getItemMeta();
			meta.setUnbreakable(true);
			held.setItemMeta(meta);
			String msg = prefix + Util.color("&eYour &f" + held.getType().name() + " &eis now unbreakable!");
			p.sendMessage(msg);
		} else {
			String error = prefix + "You cannot create unbreakable AIR!";
			p.sendMessage(error);
		}
	}
}