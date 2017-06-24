package com.SirBlobman.blobcatraz.compat;

import static com.SirBlobman.blobcatraz.utility.Util.str;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.VaultUtil;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class BPlaceholders extends EZPlaceholderHook {
	public BPlaceholders() {super(Core.INSTANCE, "blobcatraz");}
	
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
			String s = str(tokens);
			return s;
		} else return null;
	}
}