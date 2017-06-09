package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandDelHome extends ICommand {
	public CommandDelHome() {super("delhome", "[name]", "blobcatraz.player.delhome", "deletehome", "removehome", "remhome");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String name = Util.finalArgs(0, args);
		if (ConfigDatabase.homeExists(p, name)) {
			ConfigDatabase.delHome(p, name);
			String msg = prefix + "You deleted your home called &2" + name + "&r.";
			p.sendMessage(msg);
		} else {
			String error = prefix + "You don't have a home called '" + name + "'.";
			p.sendMessage(error);
		}
	}
}