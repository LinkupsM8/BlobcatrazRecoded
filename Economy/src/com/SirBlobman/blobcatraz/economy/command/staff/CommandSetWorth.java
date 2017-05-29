package com.SirBlobman.blobcatraz.economy.command.staff;

import static com.ToonBasic.blobcatraz.logger.EconomyLog.print;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigWorth;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetWorth extends ICommand {
	public CommandSetWorth() {super("setworth", "", "blobcatraz.staff.setworth", "setvalue");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
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