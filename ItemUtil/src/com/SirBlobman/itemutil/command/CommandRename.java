package com.SirBlobman.itemutil.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandRename extends PlayerCommand {
	public CommandRename() {super("rename", "<name>", "blobcatraz.player.rename");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		if(args.length > 0) {
			String name = Util.color(Util.finalArgs(0, args));
			ItemStack is = pi.getItemInMainHand();
			if(is != null) {
				ItemMeta meta = is.getItemMeta();
				meta.setDisplayName(name);
				is.setItemMeta(meta);
				p.sendMessage("Your item was renamed to " + name);
			} else {
				String error = "You can't rename air";
				p.sendMessage(error);
			}
		}
	}
}