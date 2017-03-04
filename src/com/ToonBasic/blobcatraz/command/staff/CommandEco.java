package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandEco extends ICommand {
	public CommandEco() {super("eco", "<give/take/reset/set> <player> [amount]", "blobcatraz.staff.economy", "economy");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 1) {
			String tar = args[1];
			Player t = Bukkit.getPlayer(tar);
			if(t != null) {
				String cname = cs.getName();
				String tname = t.getName();
				String sub = args[0];
				if(sub.equals("reset")) {
					ConfigDatabase.setBalance(t, 0.0D);
					cs.sendMessage("You reset the balance of " + tname);
					t.sendMessage("Your balance was reset by " + cname);
				} else if(args.length > 2) {
					try {
						String amt = args[2];
						double amount = Double.parseDouble(amt);
						if(sub.equals("give")) {
							ConfigDatabase.deposit(t, amount);
							cs.sendMessage("You gave " + Util.money(amount) + " to " + tname);
							t.sendMessage("You received " + Util.money(amount));
						} else if(sub.equals("take")) {
							ConfigDatabase.withdraw(t, amount);
							cs.sendMessage("You took " + Util.money(amount) + " from " + tname);
							t.sendMessage("You lost " + Util.money(amount));
						} else if(sub.equals("set")) {
							ConfigDatabase.setBalance(t, amount);
							cs.sendMessage("You set " + PlayerUtil.possesive(t) + " balance to " + Util.money(amount));
							t.sendMessage("Your balance was set to " + Util.money(amount));
						} else {
							String error = Language.INCORRECT_USAGE;
							cs.sendMessage(error);
						}
					} catch(Exception ex) {
						String error = args[2] + " is not a Number";
						cs.sendMessage(error);
					}
				} else {
					String error = Language.INCORRECT_USAGE;
					cs.sendMessage(error);
				}
			}
		}
	}
}