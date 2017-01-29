package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandAFK extends ICommand implements Listener {
	public CommandAFK() {super("afk", "[reason]", null, "awayfromkeyboard");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
	}
}