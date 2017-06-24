package com.SirBlobman.blobcatraz.economy.command.staff;

import static com.SirBlobman.blobcatraz.log.EconomyLog.print;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigWorth;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandSetWorth extends PlayerCommand {
	public CommandSetWorth() {super("setworth", "", "blobcatraz.staff.setworth", "setvalue");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			double worth = NumberUtil.getDouble(args[0]);
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(!ItemUtil.air(is)) {
				Material mat = is.getType();
				short data = is.getDurability();
				String item = mat.name() + ":" + data;
				ConfigWorth.setWorth(is, worth);
				String msg = prefix + Util.color("&eYou set the value of &a" + item + "&e to " + NumberUtil.money(worth));
				p.sendMessage(msg);

				String log = Util.color("&a" + p.getName() + " &fchanged the value of &c" + ItemUtil.name(is) + " &fto " + NumberUtil.money(worth));
				print(log);
			}
		} else {
			String error = prefix + "You are not Mr. Ohare, do not try to charge for AIR!";
			p.sendMessage(error);
		}
	}
}