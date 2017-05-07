package com.SirBlobman.itemutil.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.itemutil.util.ItemUtil;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetLore extends ICommand {
	public CommandSetLore() {super("setlore", "<lore>", "blobcatraz.player.addlore");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		if(args.length > 0) {
			String vals = Util.finalArgs(0, args);
			String[] lore = toLore(vals);
			ItemStack is = pi.getItemInMainHand();
			if(ItemUtil.air(is)) {
				String error = prefix + "You cannot set the lore of AIR";
				p.sendMessage(error);
			} else {
				ItemUtil.setLore(is, lore);
				String name = ItemUtil.name(is);
				List<String> list = ItemUtil.lore(is);
				String msg = prefix + Util.color("Set the lore of your &a" + name + " &fto:\n" + Util.formatList('-', list));
				p.sendMessage(msg);
			}
		}
	}
	
	private String[] toLore(String s) {
		String[] lore = s.split("\\n|/n");
		return lore;
	}
}