package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.listener.sign.ListenSellAll.gui;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSell extends ICommand {
	public CommandSell() {super("sell", "<hand/all>", "blobcatraz.player.sell");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String sub = args[0].toLowerCase();
			if(sub.equals("hand")) {
				PlayerInventory pi = p.getInventory();
				ItemStack is = pi.getItemInMainHand();
				if(is != null && is.getType() != Material.AIR) {
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
}