package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.WordUtil;

@PlayerOnly
public class CommandSkull extends ICommand {
	public CommandSkull() {super("skull", "<player>", "blobcatraz.staff.skull", "head");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if (args.length > 0) {
			String name = args[0];
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			ItemMeta meta = is.getItemMeta();
			SkullMeta skull = (SkullMeta) meta;
			String display = Util.color("&r" + WordUtil.possesive(name) + " Head");
			skull.setDisplayName(display);
			skull.setOwner(name);
			is.setItemMeta(skull);
			
			PlayerInventory pi = p.getInventory();
			pi.addItem(is);
		} else p.sendMessage("Missing Arguments: Did you mean /skull [player]");
	}
}