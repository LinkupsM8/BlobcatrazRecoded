package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandChangeType extends ICommand {
	public CommandChangeType() {super("changetype", "<material>", "blobcatraz.special.changetype", "changemat", "changematerial");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
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