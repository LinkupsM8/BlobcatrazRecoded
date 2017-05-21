package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.WordUtil;

@PlayerOnly
public class CommandBalance extends ICommand {
	public CommandBalance() {super("balance", "[player]", "blobcatraz.player.balance", "bal");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				double bal = ConfigDatabase.balance(t);
				String balance = NumberUtil.money(bal);
				String msg = prefix + Util.color("&d" + WordUtil.possesive(t.getName()) + " &fbalance is &e" + balance);
				p.sendMessage(msg);
			} else {
				String error = String.format(Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else {
			double bal = ConfigDatabase.balance(p);
			String balance = NumberUtil.money(bal);
			String msg = prefix + Util.color("&aYour balance is &e" + balance);
			p.sendMessage(msg);
		}
	}
}