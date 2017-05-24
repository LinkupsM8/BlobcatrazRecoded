package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandTP extends ICommand {
	public CommandTP() {super("tp", "", "blobcatraz.command.tp", "teleport");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length == 0) help(p);
		else if(args.length == 1) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				p.teleport(t);
				String msg = Util.color(prefix + "You teleported yourself to &a" + t.getName());
				p.sendMessage(msg);
			} else {
				String error = prefix + Language.INVALID_TARGET;
				p.sendMessage(error);
			}
		} else if(args.length == 2) {
			String target1 = args[0];
			String target2 = args[1];
			Player t1 = Bukkit.getPlayer(target1);
			Player t2 = Bukkit.getPlayer(target2);
			if(t1 != null) {
				if(t2 != null) {
					t1.teleport(t2);
					String msg = Util.color(prefix + "You teleported &a" + t1.getName() + " &rto &a" + t2.getName());
					p.sendMessage(msg);
				} else {
					String error = prefix + Language.INVALID_TARGET + ": 2";
					p.sendMessage(error);
				}
			} else {
				String error = prefix + Language.INVALID_TARGET + ": 1";
				p.sendMessage(error);
			}
		} else if(args.length == 3 || args.length == 5) {
			Location o = p.getLocation();
			Location n = location(o, args);
			p.teleport(n);
			String msg = Util.color(prefix + "You teleported to \n&a" + string(n));
			p.sendMessage(msg);
		} else if(args.length == 4 || args.length == 6) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				String[] args2 = Util.subArgs(1, args);
				Location o = t.getLocation();
				Location n = location(o, args2);
				t.teleport(n);
				String msg = Util.color(prefix + "You teleported &a" + t.getName() + " &rto \n&a" + string(n));
				p.sendMessage(msg);
			} else {
				String error = prefix + Language.INVALID_TARGET;
				p.sendMessage(error);
			}
		} else help(p);
	}
	
	private void help(Player p) {
		String[] help = Util.color(
			prefix + "Teleportation Usage:",
			"&f/tp <player> OR",
			"&f/tp <player1> <player2> OR",
			"&f/tp <x> <y> <z> OR",
			"&f/tp <x> <y> <z> <yaw> <pitch> OR",
			"&f/tp <player> <x> <y> <z> OR",
			"&f/tp <player> <x> <y> <z> <yaw> <pitch>"
		);
		p.sendMessage(help);
	}
	
	private Location location(Location o, String... args) {
		World ow = o.getWorld();
		double ox = o.getX();
		double oy = o.getY();
		double oz = o.getZ();
		float yaw = o.getYaw();
		float pit = o.getPitch();
		
		String sx = args[0];
		String sy = args[1];
		String sz = args[2];
		String syaw = "" + yaw;
		String spit = "" + pit;
		if(args.length > 3) {
			syaw = args[3];
			spit = args[4];
		}

		double nx = NumberUtil.relative(sx, ox);
		double ny = NumberUtil.relative(sy, oy);
		double nz = NumberUtil.relative(sz, oz);
		float nyaw = (float) NumberUtil.relative(syaw, yaw);
		float npit = (float) NumberUtil.relative(spit ,pit);
		
		Location n = new Location(ow, nx, ny, nz, nyaw, npit);
		return n;
	}
	
	private String string(Location l) {
		if(l == null) return "null";
		else {
			String x = NumberUtil.cropDecimal(l.getX(), 2);
			String y = NumberUtil.cropDecimal(l.getY(), 2);
			String z = NumberUtil.cropDecimal(l.getZ(), 2);
			String yaw = NumberUtil.cropDecimal(l.getYaw(), 2);
			String pit = NumberUtil.cropDecimal(l.getPitch(), 2);
			String s = Util.color(
				"&aX: &f" + x + 
				", &aY: &f" + y + 
				", &aZ: &f" + z + 
				", &aYaw: &f" + yaw + 
				", &aPitch: &f" + pit
			);
			return s;
		}
	}
}