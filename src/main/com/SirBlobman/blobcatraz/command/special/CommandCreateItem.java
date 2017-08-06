package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigCustomItems;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

public class CommandCreateItem extends PlayerCommand {
	public CommandCreateItem() {super("createitem", "<id>", "blobcatraz.special.createitem", "customitem", "newitem", "additem", "createcustomitem");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(ItemUtil.air(is)) {
			String error = prefix + "AIR is not custom...";
			p.sendMessage(error);
		} else {
			String id = Util.finalArgs(0, args);
			String ns = WordUtil.asID(id);
			ConfigCustomItems.create(id, is);
			String msg = Util.format(prefix + "You created a custom item with the ID: &a%1s", ns);
			p.sendMessage(msg);
		}
	}
}
