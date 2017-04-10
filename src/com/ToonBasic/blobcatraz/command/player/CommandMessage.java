package com.ToonBasic.blobcatraz.command.player;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandMessage extends ICommand {
    public static Map<CommandSender, CommandSender> reply = Util.newMap();
    public CommandMessage() {super("msg", "<player> <message>", "blobcatraz.player.msg", "message", "w", "whisper", "tell");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 1) {
        	String target = args[0];
        	CommandSender t = null;
        	if(target.equalsIgnoreCase("console")) t = Bukkit.getConsoleSender();
        	else t = Bukkit.getPlayer(target);
        	if(t != null) {
            	String msg = Util.finalArgs(1, args);
            	msg = Util.color(msg);
            	msg(cs, t, msg);
        	} else {
        		String error = Language.INVALID_TARGET;
        		cs.sendMessage(error);
        	}
        }
    }
    
    public static void msg(CommandSender cs, CommandSender t, String msg) {
    	String format1 = Util.color("&e[&ame&e \u2192 &a%1s&e] &f%2s");
    	String format2 = Util.color("&e[&a%1s&e \u2192 &ame&e] &f%2s");
    	cs.sendMessage(String.format(format1, t.getName(), msg));
    	t.sendMessage(String.format(format2, cs.getName(), msg));
    	reply.put(cs, t);
    	reply.put(t, cs);
    }
}