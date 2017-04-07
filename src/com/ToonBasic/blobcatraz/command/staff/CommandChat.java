package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

import static com.ToonBasic.blobcatraz.utility.Util.color;

public class CommandChat extends ICommand {
    public static boolean isChatMuted = false;
    public CommandChat() {super("chat", "[enable/disable/clear]", "blobcatraz.player.chat", "ch");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
    	if (args[0].equals("disable")) {
    		cs.sendMessage(prefix + "You have muted the chat!");
    		isChatMuted = true;
    		Bukkit.broadcastMessage(color(prefix + "The chat has been muted by &c" + cs.getName()));
    	} else if (args[0].equals("enable")) {
    		cs.sendMessage(prefix + "You have unmuted the chat!");
    		isChatMuted = false;
    		Bukkit.broadcastMessage(color(prefix + "The chat has been unmuted by &c" + cs.getName()));
    	} else if (args[0].equals("clear")) {
			for (int i = 0; i < 100; i++) {
				Bukkit.broadcastMessage("");
			}
			Bukkit.broadcastMessage("|-------------------+====+-------------------|");
			Bukkit.broadcastMessage(color(" The chat has been cleared by &c" + cs.getName() + "&f."));
			Bukkit.broadcastMessage("|-------------------+====+-------------------|");
		}
    }
}