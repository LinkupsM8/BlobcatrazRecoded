package com.SirBlobman.blobcatraz.command.staff;

import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandTempBan extends ICommand {
	public static final String SITE = CommandBan.SITE;
	private static final long MILLISECOND = 1L;
	private static final long SECOND = MILLISECOND * 1000L;
	private static final long MINUTE = SECOND * 60L;
	private static final long HOUR = MINUTE * 60L;
	private static final long DAY = HOUR * 24L;
	private static final long WEEK = DAY * 7L;
	private static final long YEAR = DAY * 365L;
	public CommandTempBan() {super("tempban", "<player> <time> <reason>", "blobcatraz.staff.tempban", "temporaryban");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
		if(ot != null) {
			String time = args[1];
			Date end = date(time);
			String reason = Util.finalArgs(2, args);
			String banner = cs.getName();
			tempban(ot, banner, end, reason);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
	
	public static void tempban(OfflinePlayer op, String banner, Date end, String reason) {
		BanList bl = Bukkit.getBanList(Type.NAME);
		String s = Util.format(
			"&f%1s\n" +
			"&b&lBanned By: &f%2s\n" +
			"&b&lUntil: &f%3s" +
			"&b&lAppeal At: &f%4s",
			reason, banner, end.toString(), SITE
		);
		String name = op.getName();
		bl.addBan(name, s, end, banner);
		if(op.isOnline()) {
			Player p = op.getPlayer();
			String r = Util.color("&4&lYou are now banned!\n" + s);
			p.kickPlayer(r);
		}
		
		String b = Util.format(Util.PREFIX + "&c&l%1s &ewas temp-banned by &a%2s for:", name, banner);
		Util.broadcast(b, Util.color(reason));
	}
	
	public static Date date(String time) {
		int l = NumberUtil.getInteger(time);
		String u1 = time.substring(time.length() - 1).toLowerCase();
		
		long d = 0L;
		switch(u1) {
		case "s":
			d = l * SECOND;
			break;
		case "m":
			d = l * MINUTE;
			break;
		case "h":
			d = l * HOUR;
			break;
		case "d":
			d = l * DAY;
			break;
		case "w":
			d = l * WEEK;
			break;
		case "y":
			d = l * YEAR;
			break;
		default:
			d = l * SECOND;
			break;
		}
		
		long c = System.currentTimeMillis();
		long ban = c + d;
		Date da = new Date(ban);
		return da;
	}
}