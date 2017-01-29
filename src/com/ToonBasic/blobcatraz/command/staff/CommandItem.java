package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandItem extends ICommand {
	public CommandItem() {super("item", "<item[:meta]> [amount]", "blobcatraz.staff.item", "i", "selfgive");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
	}
}