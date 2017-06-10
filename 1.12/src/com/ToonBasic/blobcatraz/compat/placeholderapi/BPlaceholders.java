package com.ToonBasic.blobcatraz.compat.placeholderapi;

import org.bukkit.entity.Player;
import static com.ToonBasic.blobcatraz.utility.Util.str;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.VaultUtil;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class BPlaceholders extends EZPlaceholderHook {
	public BPlaceholders() {super(Core.instance, "blobcatraz");}
	
	@Override
	public String onPlaceholderRequest(Player p, String id) {
		id = id.toLowerCase();
		if(id.equals("current_rank")) {
			String rank = VaultUtil.mainRank(p);
			return rank;
		} else if(id.equals("balance")) {
			double bal = ConfigDatabase.balance(p);
			String s = NumberUtil.cropDecimal(bal, 2);
			return s;
		} else if(id.equals("tokens")) {
			int tokens = ConfigDatabase.tokens(p);
			String str = str(tokens);
			return str;
		} else if(id.equals("kills")) {
			int kills = ConfigDatabase.kills(p);
			String str = str(kills);
			return str;
		} else if(id.equals("deaths")) {
			int death = ConfigDatabase.deaths(p);
			String str = str(death);
			return str;
		} else if(id.equals("kill_streak")) {
			int kills = ConfigDatabase.killStreak(p);
			String str = str(kills);
			return str;
		} else if(id.equals("kdr")) {
			double kills = ConfigDatabase.kills(p);
			double death = ConfigDatabase.deaths(p);
			
			double kdr = 0;
			if(death == 0) kdr = 0;
			else kdr = (kills / death);
			
			String str = str(kdr);
			return str;
		} else return null;
	}
}