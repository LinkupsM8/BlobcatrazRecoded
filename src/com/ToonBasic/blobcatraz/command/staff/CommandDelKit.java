package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandDelKit extends ICommand {
	public CommandDelKit() {super("delkit", "<name>", "blobcatraz.staff.delkit", "deletekit", "remkit", "removekit");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			ConfigKits.delete(name);
			String msg = Util.color(prefix + "Kit &c" + name + "&r was deleted!");
			p.sendMessage(msg);
		}
	}
}