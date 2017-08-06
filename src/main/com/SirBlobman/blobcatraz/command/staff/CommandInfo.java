package com.SirBlobman.blobcatraz.command.staff;

import java.net.InetSocketAddress;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.VaultUtil;

public class CommandInfo extends ICommand {
	public CommandInfo() {super("info", "<player>", "blobcatraz.staff.info", "whois");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t == null) {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		} else {
			String uname = t.getName();
			String cname = t.getCustomName();
			String dname = t.getDisplayName();
			String rank = VaultUtil.mainRank(t);
			InetSocketAddress ip = t.getAddress();
			Location l = t.getLocation();
			boolean fly1 = t.isFlying();
			boolean fly2 = t.getAllowFlight();
			boolean op = t.isOp();
			GameMode gm = t.getGameMode();
			double health = t.getHealth();
			String heal = NumberUtil.cropDecimal(health, 2);
			int food = t.getFoodLevel();
			double sat = t.getSaturation();
			String satu = NumberUtil.cropDecimal(sat, 2);
			boolean n = (cname == null);
			
			String[] msg = Util.color(
				"",
				prefix + "Information:",
				"&e&lUsername: &f" + str(uname),
				"&e&lDisplay Name: &f" + str(dname),
				"&e&lTab Name: &f"  + (n ? str(uname) : str(cname)),
				"&e&lRank: &f" + str(rank),
				"&e&lIP Address: &f" + str(ip),
				"&e&lCurrent Location: &f",
				str(l),
				"&e&lCan Fly: &f" + str(fly2),
				"&e&lFlying: &f" + str(fly1),
				"&e&lOP: &f" + str(op),
				"&e&lGameMode: &f" + str(gm),
				"&e&lHealth: &f" + str(heal),
				"&e&lFood: &f" + str(food),
				"&e&lSaturation: &f" + str(satu),
				""
			);
			cs.sendMessage(msg);
		}
	}
	
	private String str(Object o) {
		String ret = "";
		if(o instanceof Boolean) {
			boolean b = (boolean) o;
			String s = b ? "&2yes" : "&cno";
			ret = s;
		} else ret = Util.str(o);
		
		String c = Util.color(ret);
		return c;
	}
}