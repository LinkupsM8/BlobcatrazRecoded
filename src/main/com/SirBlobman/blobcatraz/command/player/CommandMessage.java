package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class CommandMessage extends ICommand {
	public static Map<CommandSender, CommandSender> REPLY = Util.newMap();
	public CommandMessage() {super("tell", "<player> <message>", "blobcatraz.player.message", "msg", "message", "whisper", "w");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		CommandSender ts = null;
		if(target.equalsIgnoreCase("console")) ts = Bukkit.getConsoleSender();
		else ts = Bukkit.getPlayer(target);
		
		if(ts != null) {
			String msg = Util.finalArgs(1, args);
			String c = Util.color(msg);
			msg(cs, ts, c);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
	
	public static void msg(CommandSender cs, CommandSender ts, String msg) {
		String c = Util.color(msg);
		String tname = ts.getName();
		String cname = cs.getName();
		
		String msg1 = Util.format("&e[&ame&e \u2192 &a%1s&e] &f%2s", tname, c);
		String msg2 = Util.format("&e[&a%1s&e \u2192 &ame&e] &f%2s", cname, c);
		cs.sendMessage(msg1);
		ts.sendMessage(msg2);
		REPLY.put(cs, ts);
		REPLY.put(ts, cs);
	}
}