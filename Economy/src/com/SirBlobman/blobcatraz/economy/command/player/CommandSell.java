package com.SirBlobman.blobcatraz.economy.command.player;

import static com.SirBlobman.blobcatraz.listener.sign.ListenSellAll.gui;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandSell extends PlayerCommand {
	public CommandSell() {super("sell", "<hand/all>", "blobcatraz.player.sell");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("hand")) {
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(!ItemUtil.air(is)) {
				Material mat = is.getType();
				short data = is.getDurability();
				String name = mat.name() + ":" + data; 
				int amount = is.getAmount();
				double worth = ItemUtil.worth(is);
				is.setAmount(0);
				ConfigDatabase.deposit(p, worth);
				String msg = prefix + Util.color("&eYou sold &a" + amount + " &eof &a" + name + " &efor " + NumberUtil.money(worth));
				p.sendMessage(msg);
			} else {
				String error = prefix + "You cannot sell AIR!";
				p.sendMessage(error);
			}
		} else if(sub.equals("all")) {
			gui(p);
			PlayerUtil.action(p, "Opening Sell All GUI");
		} else {
			String error = prefix + Language.INCORRECT_USAGE;
			p.sendMessage(error);
		}
	}
}