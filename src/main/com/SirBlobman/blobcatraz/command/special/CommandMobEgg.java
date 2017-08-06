package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class CommandMobEgg extends PlayerCommand {
	public CommandMobEgg() {super("mobegg", "<entity> [amount]", "blobcatraz.special.mobegg", "egg", "spawnegg", "monsteregg");}
	
	@Override
	public void run(Player p, String[] args) {
		String type = args[0].toUpperCase();
		int amount = 1;
		if(args.length > 1) {
			amount = NumberUtil.getInteger(args[1]);
			if(amount < 1) amount = 1;
			if(amount > 64) amount = 64;
		}
		
		try {
			EntityType et = EntityType.valueOf(type);
			if(et == null) {
				String error = Util.format("Invalid Entity Type '%1s'", type);
				p.sendMessage(error);
			} else {
				PlayerInventory pi = p.getInventory();
				ItemStack is = ItemUtil.newItem(Material.MONSTER_EGG, 1, 0);
				ItemMeta meta = is.getItemMeta();
				SpawnEggMeta se = (SpawnEggMeta) meta;
				se.setSpawnedType(et);
				is.setItemMeta(se);
				is.setAmount(amount);
				int slot = pi.firstEmpty();
				if(slot == -1) {
					String error = prefix + "Your inventory is full!";
					p.sendMessage(error);
				} else {
					pi.addItem(is);
					String msg = Util.format(prefix + "&eYou received &a%1s &b%2s &espawn egg(s)", amount, et.name());
					p.sendMessage(msg);
				}
			}
		} catch(Throwable ex) {
			String error = Util.format("Invalid Entity Type '%1s'", type);
			p.sendMessage(error);
		}
	}
}