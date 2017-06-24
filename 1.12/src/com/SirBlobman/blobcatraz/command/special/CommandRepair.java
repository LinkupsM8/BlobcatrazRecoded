package com.SirBlobman.blobcatraz.command.special;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandRepair extends PlayerCommand {
	public CommandRepair() {super("repair", "<hand/off/armor/hotbar/all>", "blobcatraz.staff.repair", "fix");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		String sub = args[0].toLowerCase();
		if(sub.equals("hand")) {
			ItemStack is = pi.getItemInMainHand();
			repair(p, is);
		} else if(sub.equals("off")) {
			ItemStack is = pi.getItemInOffHand();
			repair(p, is);
		} else if(sub.equals("armor")) {
			ItemStack[] armor = pi.getArmorContents();
			repair(p, armor);
		} else if(sub.equals("hotbar")) {
			List<ItemStack> list = Util.newList();
			for(int i = 0; i < 9; i++) {
				ItemStack is = pi.getItem(i);
				list.add(is);
			}
			
			ItemStack[] hotbar = list.toArray(new ItemStack[0]);
			repair(p, hotbar);
		} else if(sub.equals("all")) {
			ItemStack[] inv = pi.getContents();
			repair(p, inv);
		} else {
			String error = getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
	
	private void repair(Player p, ItemStack... ii) {
		for(ItemStack is : ii) {
			if(ItemUtil.air(is)) continue;
			short dur = 0;
			is.setDurability(dur);
			String name = ItemUtil.name(is);
			String msg = Util.format(prefix + "Successfully repaired your &a%1s", name);
			p.sendMessage(msg);
		}
	}
}