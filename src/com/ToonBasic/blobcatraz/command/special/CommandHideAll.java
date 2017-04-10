package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandHideAll extends ICommand {
	public CommandHideAll() {super("hideall", "", "blobcatraz.special.hideflags", "hideflags", "noflags");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
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