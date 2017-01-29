package com.ToonBasic.blobcatraz.command.staff;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.PublicHandlers;
import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandTempBan extends ICommand {
	public CommandTempBan() {super("tempban", "<player> <time> <reason>", "blobcatraz.staff.temban");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 2) {
			String target = args[0];
			String time = args[1];
			Date end1 = date(time);
			String reason = PublicHandlers.finalArgs(2, args);
			
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				BanList bl = Bukkit.getBanList(Type.NAME);
				String nreason = PublicHandlers.color("&f" + reason + "\n&f&lBanned By: &f" + cs.getName() + "\n&f&lAppeal At: &f&3&o&nhttp://blobcatraz.mc-srv.com");
				bl.addBan(t.getName(), nreason, end1, cs.getName());
                t.kickPlayer(PublicHandlers.color("&4You are banned!" + "\n&f&lReason: &f") + nreason);
			} else {
				String error = String.format(Language.INVALID_TARGET, target);
				cs.sendMessage(error);
			}
		}
	}
	
	private Date date(String time) {
		int l = (time.length() - 1);
		String unit1 = time.substring(l).toLowerCase();
		String unit2 = time.substring(0, l);
		int length = Integer.parseInt(unit2);
		
		long mil = 1L;
		long sec = mil * 1000L;
		long min = sec * 60L;
		long hou = min * 60L;
		long day = hou * 24L;
		
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
		default:
			banTime = length * sec;
			break;
		}
		
		long system = System.currentTimeMillis();
		long ban = system + banTime;
		return new Date(ban);
	}
}