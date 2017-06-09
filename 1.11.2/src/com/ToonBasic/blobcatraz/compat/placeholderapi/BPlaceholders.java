package com.ToonBasic.blobcatraz.compat.placeholderapi;

import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.VaultUtil;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class BPlaceholders extends EZPlaceholderHook {
	public BPlaceholders() {super(Core.instance, "blobcatraz");}
	
	@Override
	public String onPlaceholderRequest(Player p, String id) {
		if(id.equals("current_rank")) return VaultUtil.mainRank(p);
		else if(id.equals("balance")) {
			double bal = ConfigDatabase.balance(p);
			String s = NumberUtil.cropDecimal(bal, 2);
			return s;
		}
		else if(id.equals("tokens")) return Util.toString(ConfigDatabase.tokens(p));
		else if(id.equals("kills")) return Util.toString(ConfigDatabase.kills(p));
		else if(id.equals("deaths")) return Util.toString(ConfigDatabase.deaths(p));
		else if(id.equals("kill_streak")) return Util.toString(ConfigDatabase.killStreak(p));
		else if(id.equals("kdr")) {
			double kills = ConfigDatabase.kills(p);
			double deaths = ConfigDatabase.deaths(p);
			if(deaths == 0) return Util.toString(kills);
			else {
				double kdr = (kills/deaths);
				return Util.toString(kdr);
			}
		}
		else return null;
	}
}