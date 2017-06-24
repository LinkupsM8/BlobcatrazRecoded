package com.SirBlobman.itemutil.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandChangeType extends PlayerCommand {
	public CommandChangeType() {super("changetype", "<material>", "blobcatraz.special.changetype", "changemat", "changematerial");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		String id = Util.finalArgs(0, args).toLowerCase().replace(' ', '_');
		ItemStack to = ItemUtil.getItem(id, 1, (short) 0);
		if(ItemUtil.air(to)) {
			String error = prefix + Util.color("Invalid Material: &c" + id);
			p.sendMessage(error);
		} else {
			Material mat = to.getType();
			is.setType(mat);
			String msg = prefix + Util.color("Your item is now made of a new material");
			p.sendMessage(msg);
		}
	}
}