package com.ToonBasic.blobcatraz.command.staff;

import java.net.InetSocketAddress;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.VaultUtil;
import com.ToonBasic.blobcatraz.utility.WordUtil;

public class CommandInfo extends ICommand {
	public CommandInfo() {super("info", "<player>", "blobcatraz.staff.info", "getip");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t == null) {
			String error = prefix + Language.INVALID_TARGET;
			cs.sendMessage(error);
		} else {
			String disp = t.getDisplayName();
			String cname = t.getCustomName();
			boolean n = (cname == null);
			String name = t.getName();
			String rank = VaultUtil.mainRank(t);
			InetSocketAddress ip = t.getAddress();
			World w = t.getWorld();
			Location l = t.getLocation();
			boolean fly = t.isFlying();
			boolean canFly = t.getAllowFlight();
			boolean op = t.isOp();
			GameMode gm = t.getGameMode();
			double health = t.getHealth();
			int food = t.getFoodLevel();
			double sat = t.getSaturation();
			
			String[] msg = Util.color(new String[] {
				"",
				prefix + "Information:",
				"&e&lUsername: &f" + name,
				"&e&lDisplay Name: &f" + disp,
				"&e&lTab Name: &f" + (n ? name : cname),
				"&e&lRank: &f" + rank,
				"&e&lIP Address: &f" + string(ip),
				"&e&lCurrent World: &f" + w.getName(),
				"&e&lCurrent Location: &f" + string(l),
				"&e&lCan Fly: &f" + string(canFly),
				"&e&lFlying: &f" + string(fly),
				"&e&lOP: &f" + string(op),
				"&e&lGame Mode: &f" + string(gm),
				"&e&lHealth: &f" + NumberUtil.cropDecimal(health, 2),
				"&e&lFood: &f" + food,
				"&e&lSaturation: &f" + NumberUtil.cropDecimal(sat, 2),
				""
			});
			cs.sendMessage(msg);
		}
	}
	
	private String string(Object o) {
		String ret = "";
		if(o instanceof Boolean) {
			boolean b = (boolean) o;
			ret = b ? "&2yes" : "&cno";
		} else if(o instanceof Location) {
			Location l = (Location) o;
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockX();
			String loc = "X: " + x + ", Y: " + y + ", Z: " + z;
			ret = loc;
		} else if(o instanceof GameMode) {
			GameMode gm = (GameMode) o;
			String name = gm.name();
			ret = WordUtil.capitalize(name);
		} else if(o instanceof InetSocketAddress) {
			InetSocketAddress ip = (InetSocketAddress) o;
			ret = ip.getHostString();
		} else ret = o.toString();
		
		ret = Util.color(ret);
		return ret;
	}
}