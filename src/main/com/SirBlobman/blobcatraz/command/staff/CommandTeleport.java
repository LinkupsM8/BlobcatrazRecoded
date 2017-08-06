package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandTeleport extends PlayerCommand {
	public CommandTeleport() {super("tp", "", "blobcatraz.staff.teleport", "teleport");}
	
	@Override
	public void run(Player p, String[] args) {
		int l = args.length;
		if(l == 0) help(p);
		else if(l == 1) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				p.teleport(t);
				String name = t.getName();
				String msg = Util.format(prefix + "You teleported yourself to &a%1s", name);
				p.sendMessage(msg);
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else if(l == 2) {
			String target1 = args[0];
			String target2 = args[1];
			Player t1 = Bukkit.getPlayer(target1);
			Player t2 = Bukkit.getPlayer(target2);
			if(t1 != null) {
				if(t2 != null) {
					t1.teleport(t2);
					String name1 = t1.getName();
					String name2 = t2.getName();
					String msg = Util.format(prefix + "You teleported &a%1s&r to &a%2s&r.", name1, name2);
					p.sendMessage(msg);
				} else {
					String error = Util.format(prefix + Language.INVALID_TARGET, target2);
					p.sendMessage(error);
				}
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target1);
				p.sendMessage(error);
			}
		} else if(l == 3 || l == 5) {
			Location o = p.getLocation();
			Location n = location(o, args);
			p.teleport(n);
			String msg = Util.format(prefix + "You teleported yourself to \n&a%1s", Util.str(n));
			p.sendMessage(msg);
		} else if(l == 4 || l == 6) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				String[] sub = Util.subArgs(1, args);
				Location o = t.getLocation();
				Location n = location(o, sub);
				t.teleport(n);
				String name = t.getName();
				String msg = Util.format(prefix + "You teleported &a%1s&f to \n&a%2s", name, Util.str(n));
				p.sendMessage(msg);
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else help(p);
	}
	
	private void help(Player p) {
		String[] help = Util.color(
			prefix + "Teleport Usage:",
			"&f/tp <player>",
			"&f/tp <player1> <player2>",
			"&f/tp <x> <y> <z> [yaw] [pitch]",
			"&f/tp <player> <x> <y> <z> [yaw] [pitch]"
		);
		p.sendMessage(help);
	}
	
	private Location location(Location o, String... args) {
		World ow = o.getWorld();
		double ox = o.getX();
		double oy = o.getY();
		double oz = o.getZ();
		float oya = o.getYaw();
		float opi = o.getPitch();
		
		String sx = args[0], sy = args[1], sz = args[2],
		sya = Float.toString(oya), spi = Float.toString(opi);
		if(args.length > 3) {sya = args[3]; spi = args[4];}

		double nx = NumberUtil.relative(sx, ox);
		double ny = NumberUtil.relative(sy, oy);
		double nz = NumberUtil.relative(sz, oz);
		float nya = (float) NumberUtil.relative(sya, oya);
		float npi = (float) NumberUtil.relative(spi, opi);
		
		Location n = new Location(ow, nx, ny, nz, nya, npi);
		return n;
	}
}