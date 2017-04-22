package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandEnchant extends ICommand {
	public CommandEnchant() {super("enchant", "<enchantment> <level>", "blobcatraz.staff.enchant", "ench");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 1) {
			String ench = args[0].toUpperCase();
			String levl = args[1];
			try {
				Enchantment enchant = Enchantment.getByName(ench);
				short level = Short.parseShort(levl);
				PlayerInventory pi = p.getInventory();
				ItemStack is = pi.getItemInMainHand();
				if(is != null) {
					if(level == 0) {
						is.removeEnchantment(enchant);
					} else {
						is.addUnsafeEnchantment(enchant, level);
					}
					p.sendMessage("Successfully enchanted your item!");
				} else {
					String error = "You cannot enchant AIR";
					p.sendMessage(error);
				}
			} catch(Exception ex) {
				String error = Language.INCORRECT_USAGE + ": /enchant " + getUsage();
				String valid = "Valid Enchants:\n" + valid();
				p.sendMessage(error);
				p.sendMessage(valid);
			}
		}
	}
	
	public String valid() {
		StringBuilder build = new StringBuilder();
		for(Enchantment e : Enchantment.values()) {
			if(build.length() > 0) build.append(", ");
			build.append(e.getName());
		}
		return build.toString();
	}
}