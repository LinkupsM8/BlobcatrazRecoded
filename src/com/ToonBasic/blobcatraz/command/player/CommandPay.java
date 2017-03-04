package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandPay extends ICommand {
	public CommandPay() {super("pay", "<player> <amount>", "blobcatraz.player.pay");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		try {
			double bal = ConfigDatabase.balance(p);
			String tar = args[0];
			Player t = Bukkit.getPlayer(tar);
			if(t != null) {
				String mon = args[1];
				double pay = Double.parseDouble(mon);
				if(pay <= bal) {
					ConfigDatabase.withdraw(p, pay);
					ConfigDatabase.deposit(t, pay);
					p.sendMessage("You sent " + Util.money(pay) + " to " + t.getName());
					t.sendMessage("You received " + Util.money(pay) + " from " + p.getName());
				} else {
					String error = "You don't have enough money! You only have " + Util.money(bal);
					p.sendMessage(error);
				}
			} else {
				String error = Language.INVALID_TARGET + ": " + tar;
				p.sendMessage(error);
			}
		} catch(Exception ex) {
			String error = "Invalid Usage! Use /pay <player> <amount>";
			p.sendMessage(error);
		}
	}
}