package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandRepair extends ICommand {
	public CommandRepair() {super("repair", "[hand/offhand/armor/hotbar/all]", "blobcatraz.staff.repair", "fix");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		if(args.length > 0) {
			String sub = args[0].toLowerCase();
			if(sub.equals("hand")) {
				ItemStack is = pi.getItemInMainHand();
				repair(p, is);
			} else if(sub.equals("offhand")) {
				ItemStack is = pi.getItemInOffHand();
				repair(p, is);
			} else if(sub.equals("armor")) {
				ItemStack[] armor = pi.getArmorContents();
				repair(p, "armor", armor);
			} else if(sub.equals("hotbar")) {
				List<ItemStack> list = Util.newList();
				for(int i = 0; i < 9; i++) {
					ItemStack is = pi.getItem(i);
					list.add(is);
				}
				ItemStack[] iss = list.toArray(new ItemStack[0]);
				repair(p, "hotbar", iss);
			} else if(sub.equals("all")) {
				ItemStack[] inv = pi.getContents();
				repair(p, "entire inventory", inv);
			} else {
				String error = prefix + getFormattedUsage(getCommandUsed());
				p.sendMessage(error);
			}
		} else {
			String cmd = "repair hand";
			p.performCommand(cmd);
		}
	}
	
	private void nothing(Player p) {
		String error = prefix + "AIR cannot be repaired!";
		p.sendMessage(error);
	}
	
	private void repair(ItemStack is) {
		short dur = (short) 0;
		is.setDurability(dur);
	}
	
	private void repair(Player p, ItemStack is) {
		if(ItemUtil.air(is)) nothing(p);
		else {
			repair(is);
			String msg = prefix + "Successfully repaired your " + ItemUtil.name(is);
			p.sendMessage(msg);
		}
	}
	
	private void repair(Player p, String s, ItemStack... iss) {
		for(ItemStack is : iss) repair(is);
		String msg = prefix + "Successfuly repaired your " + s + "!";
		p.sendMessage(msg);
	}
}