package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.command.player.CommandMessage.msg;
import static com.ToonBasic.blobcatraz.command.player.CommandMessage.reply;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandReply extends ICommand {
    public CommandReply() { super("reply", "<message>", "blobcatraz.player.msg", "r");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 0) {
        	CommandSender t = reply.get(cs);
        	if(t != null) {
            	String msg = Util.finalArgs(0, args);
            	msg = Util.color(msg);
            	msg(cs, t, msg);
        	} else {
        		String error = prefix + "You have nobody to reply to!";
        		cs.sendMessage(error);
        	}
        }
    }
}