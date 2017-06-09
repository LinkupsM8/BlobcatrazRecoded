package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigCustomItems;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandCreateItem extends ICommand {
	public CommandCreateItem() {super("creatitem", "[id]", "blobcatraz.staff.createitem", "customitem", "newitem", "createshopitem");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(ItemUtil.air(is)) {
			String error = prefix + "You cannot create AIR";
			p.sendMessage(error);
		} else {
			String id = Util.finalArgs(0, args);
			id = id.toLowerCase();
			id = id.replace(' ', '_');
			ConfigCustomItems.create(id, is);
			String msg = prefix + Util.color("You created a custom item with the id: &a" + id);
			p.sendMessage(msg);
		}
	}
}
