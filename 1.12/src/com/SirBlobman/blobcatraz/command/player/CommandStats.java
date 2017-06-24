package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class CommandStats extends PlayerCommand {
	public CommandStats() {super("stats", "[player]", "blobcatraz.stats");}
	
	@Override
	public void run(Player p, String[] args) {
		String target = p.getName();
		if(args.length > 0) target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) stats(p, t);
		else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
	
	private void stats(Player p, Player o) {
		String name = o.getName();
		int kills = p.getStatistic(Statistic.PLAYER_KILLS);
		int deaths = p.getStatistic(Statistic.DEATHS);
		double kk = kills, dd = deaths;
		double kd = (kk / dd);
		if(kd == Double.NaN || kd == 0) kd = kills;
		String kdr = NumberUtil.cropDecimal(kd, 2);
		
		String[] msg = Util.color(
			"&8&m--------&8> &eStats &8<&m--------",
			"&3&lUsername&8: &e" + name,
			"&3&lKills&8: &e" + kills,
			"&3&lDeaths&8: &e" + deaths,
			"&3&lKDR&8: &e" + kdr,
			"&8&m--------------------------------"
		);
		p.sendMessage(msg);
	}
}