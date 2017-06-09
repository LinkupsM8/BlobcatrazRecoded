package com.ToonBasic.blobcatraz.command.staff;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandTempBan extends ICommand {
	public CommandTempBan() {super("tempban", "<player> <time> <reason>", "blobcatraz.staff.temban");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 2) {
			String target = args[0];
			String time = args[1];
			Date end = date(time);
			String reason = Util.finalArgs(2, args);
			
			OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
			if(ot != null) {
				String banner = cs.getName();
				tempBan(ot, banner, end, reason);
			} else {
				String error = String.format(Language.INVALID_TARGET, target);
				cs.sendMessage(error);
			}
		}
	}
	
	private static final String SITE = Util.color("&3&n&ohttp://blobcatraz.mc-srv.com");
	public static void tempBan(OfflinePlayer op, String banner, Date end, String reason) {
		BanList bl = Bukkit.getBanList(Type.NAME);
    	String nreason = Util.color("&f" + reason + "\n"
    			+ "&b&lBanned By: &f" + banner + "\n"
    			+ "&b&lUntil: &f" + end.toString() + "\n"
    			+ "&b&lAppeal At: &f" + SITE);
    	String name = op.getName();
    	bl.addBan(name, nreason, end, banner);
    	if(op.isOnline()) {
    		Player p = op.getPlayer();
    		p.kickPlayer(Util.color("&4&lYou are now banned!" + "\n&b&lReason: &r" + nreason));
    		String bcast = Util.prefix + Util.color("&a&l" + name + " &ewas banned by " + banner + " for:\n");
    		Util.broadcast(bcast, Util.color(reason));
    	}
	}
	
	public static Date date(String time) {
		int l = (time.length() - 1);
		String unit1 = time.substring(l).toLowerCase();
		String unit2 = time.substring(0, l);
		int length = Integer.parseInt(unit2);
		
		long mil = 1L;
		long sec = mil * 1000L;
		long min = sec * 60L;
		long hou = min * 60L;
		long day = hou * 24L;
		long wee = day * 7L;
		long yea = day * 365L;
		
		long banTime = 0L;
		switch(unit1) {
		case "s":
			banTime = length * sec;
			break;
		case "m":
			banTime = length * min;
			break;
		case "h":
			banTime = length * hou;
			break;
		case "d":
			banTime = length * day;
			break;
		case "w":
			banTime = length * wee;
			break;
		case "y":
			banTime = length * yea;
			break;
		default:
			banTime = length * sec;
			break;
		}
		
		long system = System.currentTimeMillis();
		long ban = system + banTime;
		Date d = new Date(ban);
		return d;
	}
}