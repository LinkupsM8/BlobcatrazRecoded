package com.SirBlobman.blobcatraz.economy.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandBalance extends PlayerCommand {
	public CommandBalance() {super("balance", "[player]", "blobcatraz.player.balance", "bal");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				double bal = ConfigDatabase.balance(t);
				String balance = NumberUtil.money(bal);
				String msg = prefix + Util.color("&d" + WordUtil.possessive(t.getName()) + " &fbalance is &e" + balance);
				p.sendMessage(msg);
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
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