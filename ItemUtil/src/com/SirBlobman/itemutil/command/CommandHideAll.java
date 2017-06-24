package com.SirBlobman.itemutil.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandHideAll extends PlayerCommand {
	public CommandHideAll() {super("hideall", "", "blobcatraz.special.hideflags", "hideflags", "noflags");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is != null) {
			ItemMeta meta = is.getItemMeta();
			meta.addItemFlags(ItemFlag.values());
			is.setItemMeta(meta);
			String msg = "Your item's enchants, unbreakable status, and breakable blocks are now invisible!";
			p.sendMessage(msg);
		} else {
			String error = prefix + "Air is already invisble";
			p.sendMessage(error);
		}
	}
}