package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandStats extends ICommand {
	public CommandStats() {super("stats", "[player]", "blobcatraz.stats");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) stats(p, t);
			else {
				String error = prefix + Language.INVALID_TARGET;
				p.sendMessage(error);
			}
		} else stats(p, p);
	}
	
	private void stats(Player p, Player o) {
		int kills = ConfigDatabase.kills(o);
		int deaths = ConfigDatabase.deaths(o);
		int streak = ConfigDatabase.killStreak(o);
		double kdr = (((double) kills) / ((double) deaths));
		String kd = NumberUtil.cropDecimal(kdr, 2);
		String[] msg = Util.color(Util.newArray(
			"&8&m----------------&8> &eStats &8<&8&m----------------",
			"&3&lUsername&8: &e" + o.getName(),
			"&3&lKills&8: &e" + kills,
			"&3&lDeaths&8: &e" + deaths,
			"&3&lKDR&8: &e" + kd,
			"&3&lStreak&8: &e" + streak,
			"&8&m---------------------------------------"
		));
		p.sendMessage(msg);
	}
}