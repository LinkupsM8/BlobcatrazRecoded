package com.ToonBasic.blobcatraz.command.player;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandTpa extends ICommand {
	public static Map<Player, Player> tplist = Util.newMap();
	public CommandTpa() {super("tpa", "<player>", "blobcatraz.player.tpa", "tpask");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			tplist.put(t, p);
			String[] msg = Util.color(new String[] {
				prefix + "&a" + p.getName() + " &ewants to TP to you",
				"&eTo allow them, type &a/tpaccept",
				"&eTo deny them, type &c/tpdeny"
			});
			t.sendMessage(msg);
			p.sendMessage(prefix + Util.color("&eYou asked &a" + t.getName() + " &eto TP, waiting for their response!"));
		} else {
			String error = Language.INVALID_TARGET;
			p.sendMessage(error);
		}
	}
}