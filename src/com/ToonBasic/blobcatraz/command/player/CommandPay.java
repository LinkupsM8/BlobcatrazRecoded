package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.logger.EconomyLog;
import com.ToonBasic.blobcatraz.utility.NumberUtil;

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