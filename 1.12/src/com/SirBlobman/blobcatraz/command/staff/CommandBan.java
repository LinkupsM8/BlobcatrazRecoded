package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandBan extends ICommand {
	public static final String SITE = Util.color("&3&o&nhttp://blobcatraz.mc-srv.com");
	public CommandBan() {super("ban", "<name> <reason>", "blobcatraz.staff.ban", "banish");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		String reason = Util.finalArgs(1, args);
		OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
		if(ot != null) {
			String name = cs.getName();
			ban(ot, name, reason);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
		
	public static void ban(OfflinePlayer op, String banner, String reason) {
		BanList bl = Bukkit.getBanList(Type.NAME);
		String s = Util.format(
			"&f%1s\n" +
			"&b&lBanned By: &f%2s\n" +
			"&b&lAppeal At: &f%3s",
			reason, banner, SITE
		);
		String name = op.getName();
		bl.addBan(name, s, null, banner);
		if(op.isOnline()) {
			Player p = op.getPlayer();
			String r = Util.color("&4&lYou are now banned!\n" + s);
			p.kickPlayer(r);
			String b = Util.format(Util.PREFIX + "&c&l%1s &ewas banned by &a%2s for:", name, banner);
			Util.broadcast(b, Util.color(reason));
		}
	}
}