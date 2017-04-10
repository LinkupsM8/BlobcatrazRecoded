package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandUnbreakable extends ICommand {
	public CommandUnbreakable() {super("unbreakable", "", "blobcatraz.special.unbreakable", "unbreaking");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
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