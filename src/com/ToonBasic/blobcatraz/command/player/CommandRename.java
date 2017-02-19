package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandRename extends ICommand {
	public CommandRename() {super("rename", "<name>", "blobcatraz.player.rename");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
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