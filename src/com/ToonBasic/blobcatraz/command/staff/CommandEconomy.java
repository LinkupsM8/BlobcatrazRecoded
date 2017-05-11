package com.ToonBasic.blobcatraz.command.staff;

import static com.ToonBasic.blobcatraz.logger.EconomyLog.print;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandEconomy extends ICommand {
	public CommandEconomy() {super("eco", "<give/reset/set/take> <player> [amount]", "blobcatraz.staff.economy", "economy");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 1) {
			String target = args[1];
			OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
			if(ConfigDatabase.hasAccount(ot)) {
				String cname = cs.getName();
				String oname = ot.getName();
				double oldBal = ConfigDatabase.balance(ot);
				String amt = Util.onlyDouble(args[2]);
				double amount = 0.0D;
				try {amount = Double.parseDouble(amt);}
				catch(Exception ex) {amount = 0.0D;}
				String sub = args[0].toLowerCase();
				if(sub.equals("give")) {
					double newBal = oldBal + amount;
					ConfigDatabase.deposit(ot, amount);
					String amt2 = Util.money(amount);
					String bal1 = Util.money(oldBal);
					String bal2 = Util.money(newBal);
					String msg1 = "You gave " + amt2 + "&f to " + oname;
					String msg2 = "You recieved " + amt2;
					msg(cs, ot, msg1, msg2);
					print(cname + " changed the balance of " + oname + " from " + bal1 + " to " + bal2);
				} else if(sub.equals("reset")) {
					double newBal = 0;
					amount = 0;
					ConfigDatabase.setBalance(ot, amount);
					String amt2 = Util.money(amount);
					String bal1 = Util.money(oldBal);
					String bal2 = Util.money(newBal);
					String msg1 = "You set the balance of " + oname + "&f to " + amt2;
					String msg2 = "You now have " + bal2;
					msg(cs, ot, msg1, msg2);
					print(cname + " changed the balance of " + oname + " from " + bal1 + " to " + bal2);
				} else if(sub.equals("set")) {
					double newBal = amount;
					ConfigDatabase.setBalance(ot, amount);
					String amt2 = Util.money(amount);
					String bal1 = Util.money(oldBal);
					String bal2 = Util.money(newBal);
					String msg1 = "You set the balance of " + oname + "&f to " + amt2;
					String msg2 = "You now have " + bal2;
					msg(cs, ot, msg1, msg2);
					print(cname + " changed the balance of " + oname + " from " + bal1 + " to " + bal2);
				} else if(sub.equals("take")) {
					double newBal = oldBal - amount;
					ConfigDatabase.withdraw(ot, amount);
					String amt2 = Util.money(amount);
					String bal1 = Util.money(oldBal);
					String bal2 = Util.money(newBal);
					String msg1 = "You took " + amt2 + " &f from " + oname;
					String msg2 = "Someone took " + amt2 + "&f from you!";
					msg(cs, ot, msg1, msg2);
					print(cname + " changed the balance of " + oname + " from " + bal1 + " to " + bal2);
				} else {
					String error = prefix + Language.INCORRECT_USAGE;
					cs.sendMessage(error);
				}
			} else {
				String error = prefix + "That player was never online!";
				cs.sendMessage(error);
			}
		}
	}
	
	private void msg(CommandSender cs, OfflinePlayer op, String msg1, String msg2) {
		msg1 = Util.color(msg1);
		msg2 = Util.color(msg2);
		cs.sendMessage(msg1);
		if(op != null && op.isOnline()) {
			Player p = op.getPlayer();
			p.sendMessage(msg2);
		}
	}
}