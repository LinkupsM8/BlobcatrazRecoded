package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandItemDB extends ICommand {
	public CommandItemDB() {super("itemdb", "", "blobcatraz.special.itemdb", "iteminfo", "itemname");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is != null) {
			Material mat = is.getType();
			String name = mat.name();
			int id = mat.getId();
			int data = is.getDurability();
			String[] msg = Util.color(new String[] {
				prefix + "Item Information:",
				" &e Material Name: &f" + name,
				" &e ID: &f" + id,
				" &e Data: &f" + data
			});
			p.sendMessage(msg);
		} else {
			String[] msg = Util.color(new String[] {
				prefix + "Item Information:",
				" &e Material Name: &fAIR",
				" &e ID: &f0",
				" &e Data:&f0"
			});
			p.sendMessage(msg);
		}
}
}