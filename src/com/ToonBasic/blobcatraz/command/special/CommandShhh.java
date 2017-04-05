package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandShhh extends ICommand {
	
	public CommandShhh() { super("shhh", "", ""); }
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) { cs.sendMessage(prefix + "\u00a78Somewhere in the world, a Polar Bear has suddenly \u00a74\u00a7lDIED\u00a78! \u00a74\u00a7l>:)"); }
}