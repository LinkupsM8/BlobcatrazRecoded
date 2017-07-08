package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandEnchant extends PlayerCommand implements TabCompleter {
	public CommandEnchant() {super("enchant", "<enchantment> <level>", "blobcatraz.staff.enchant", "ench");}
	
	@Override
	public void run(Player p, String[] args) {
		String ench = args[0].toUpperCase();
		String levl = args[1];
		try {
			Enchantment e = ItemUtil.getEnchant(ench);
			short lvl = NumberUtil.toShort(NumberUtil.getInteger(levl));
			ItemStack is = PlayerUtil.held(p);
			if(ItemUtil.air(is)) {
				String error = prefix + "You cannot enchant AIR!";
				p.sendMessage(error);
			} else {
				if(lvl <= 0) is.removeEnchantment(e);
				else is.addUnsafeEnchantment(e, lvl);
				String msg = prefix + "Successfully enchanted your item.";
				p.sendMessage(msg);
			}
		} catch(Throwable ex) {
			String error = prefix + getFormattedUsage(getCommandUsed());
			String valid = "Valid Enchants:" + Util.joinList(valid(), "\n-", 0);
			String[] ss = Util.color(error, valid);
			p.sendMessage(ss);
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args) {
		if(args.length == 1) {
			String arg = args[0];
			List<String> list = valid();
			List<String> matc = Util.matching(list, arg);
			return matc;
		} else return null;
	}
	
	private List<String> valid() {
		Map<String, Enchantment> map = ItemUtil.ENCHANTS;
		Set<String> set = map.keySet();
		List<String> list = Util.newList(set);
		return list;
	}
}