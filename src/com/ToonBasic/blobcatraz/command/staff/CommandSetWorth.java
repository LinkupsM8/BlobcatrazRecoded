package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigWorth;
import com.ToonBasic.blobcatraz.utility.Util;

import static com.ToonBasic.blobcatraz.utility.Util.print;

@PlayerOnly
public class CommandSetWorth extends ICommand {
	public CommandSetWorth() {super("setworth", "", "blobcatraz.staff.setworth", "setvalue");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String amount = args[0];
			amount = Util.onlyDouble(amount);
			double worth = Double.parseDouble(amount);
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(is != null && is.getType() != Material.AIR) {
				Material mat = is.getType();
				short data = is.getDurability();
				String item = mat.name() + ":" + data;
				ConfigWorth.setWorth(is, worth);
				String msg = prefix + Util.color("&eYou set the value of &a" + item + "&e to " + Util.money(worth));
				p.sendMessage(msg);
				print("Player " + cs.getName() + " has modified "+ is.getType() + " to " + worth);
					}
			} else {
				String error = prefix + "You are not Mr. Ohare, do not try to charge for AIR!";
				p.sendMessage(error);
			}
		}
	}