package com.SirBlobman.itemutil.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.itemutil.utility.ItemUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class CommandAddLore extends PlayerCommand {
	public CommandAddLore() {super("addlore", "<lore>", "blobcatraz.player.addlore");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		if(args.length > 0) {
			String vals = Util.finalArgs(0, args);
			String[] lore = toLore(vals);
			ItemStack is = pi.getItemInMainHand();
			if(ItemUtil.air(is)) {
				String error = prefix + "You cannot change the lore of AIR";
				p.sendMessage(error);
			} else {
				ItemUtil.addLore(is, lore);
				String name = ItemUtil.name(is);
				List<String> list = ItemUtil.lore(is);
				String msg = prefix + Util.color("Changed the lore of your &a" + name + " &fto:" + Util.joinList(list, "\n-"));
				p.sendMessage(msg);
			}
		}
	}
	
	private String[] toLore(String s) {
		String[] lore = s.split("\\n|/n");
		return lore;
	}
}