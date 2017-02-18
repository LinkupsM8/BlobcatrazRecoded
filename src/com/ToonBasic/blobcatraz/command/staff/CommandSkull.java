package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSkull extends ICommand {

	public CommandSkull() {
		super("skull", "<player>", "blobcatraz.staff.skull", "head");
	}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {

		Player p = (Player) cs;

		if (args.length > 0) {
			String pName = args[0];
			ItemStack skull = new ItemStack(Material.SKULL_ITEM);
			skull.setDurability((short) 3);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			String display = Util.color("\u00a7r" + pName + (pName.endsWith("s") ? "'" : "'s") + " Head");
			meta.setDisplayName(display);
			meta.setOwner(pName);
			skull.setItemMeta(meta);

			p.getInventory().addItem(skull);

		} else p.sendMessage("Missing Arguments: Did you mean /skull [player]");
	}

}
