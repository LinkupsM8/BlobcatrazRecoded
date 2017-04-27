package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandMobEgg extends ICommand {
	public CommandMobEgg() {super("mobegg", "<entity> [amount]", "blobcatraz.special.mobegg", "monsteregg", "spawnegg", "egg");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		String ent = args[0].toUpperCase();
		int amount = 1;
		if(args.length > 1) {
			String am = args[1];
			am = Util.onlyInteger(am);
			try{amount = Integer.parseInt(am);}
			catch(Exception ex) {amount = 1;}
		}
		
		EntityType et = EntityType.valueOf(ent);
		if(et == null) {
			String error = prefix + "Invalid Entity Type: " + ent;
			p.sendMessage(error);
		} else {
			ItemStack is = new ItemStack(Material.MONSTER_EGG);
			ItemMeta meta = is.getItemMeta();
			SpawnEggMeta egg = (SpawnEggMeta) meta;
			egg.setSpawnedType(et);
			is.setItemMeta(egg);
			is.setAmount(amount);
			int slot = pi.firstEmpty();
			if(slot == -1) {
				String error = prefix + "Your inventory is too full!";
				p.sendMessage(error);
			} else {
				pi.setItem(slot, is);
				String msg = prefix + Util.color("&eYou were given &a" + amount + " &b" + et.name() + "&e spawn egg(s)");
				p.sendMessage(msg);
			}
		}
	}
}