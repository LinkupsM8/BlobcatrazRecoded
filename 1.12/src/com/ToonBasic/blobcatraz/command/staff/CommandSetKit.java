package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetKit extends ICommand {
	public CommandSetKit() {super("setkit", "<name>", "blobcatraz.staff.createkit", "makekit", "createkit");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			PlayerInventory pi = p.getInventory();
			ConfigKits.create(pi, name);
			String msg = Util.color("You have created a kit called &c" + name);
			p.sendMessage(msg);
		}
	}
}