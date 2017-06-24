package com.SirBlobman.blobcatraz.economy.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.log.EconomyLog;
import com.SirBlobman.blobcatraz.utility.NumberUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandPay extends PlayerCommand {
	public CommandPay() {super("pay", "<player> <amount>", "blobcatraz.player.pay");}
	
	@Override
	public void run(Player p, String[] args) {
		try {
			double bal = ConfigDatabase.balance(p);
			String tar = args[0];
			Player t = Bukkit.getPlayer(tar);
			if(t != null) {
				double pay = NumberUtil.getDouble(args[1]);
				if(pay < 0) pay *= -1;
				if(pay <= bal) {
					ConfigDatabase.withdraw(p, pay);
					ConfigDatabase.deposit(t, pay);
					p.sendMessage("You sent " + NumberUtil.money(pay) + " to " + t.getName());
					t.sendMessage("You received " + NumberUtil.money(pay) + " from " + p.getName());
					EconomyLog.print(p.getName() + " payed " + NumberUtil.money(pay) + " to " + t.getName());
				} else {
					String error = "You don't have enough money! You only have " + NumberUtil.money(bal);
					p.sendMessage(error);
				}
			} else {
				String error = Language.INVALID_TARGET + ": " + tar;
				p.sendMessage(error);
			}
		} catch(Exception ex) {
			String error = prefix + getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}