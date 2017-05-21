package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandReport extends ICommand {
	public CommandReport() {super("report", "<player> <reason>", "blobcatraz.player.report");} 
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String reason = Util.finalArgs(1, args);
			reason = Util.color(reason.trim());
			for(Player o : Bukkit.getOnlinePlayers()) {
				String perm = "blobcatraz.report.see";
				if(o.hasPermission(perm)) {
					String rep = prefix + Util.color("&4" + t.getName() + " &7was reported by &2" + p.getName() + " &7for:\n&f" + reason);
					o.sendMessage(rep);
				}
			}
			String msg = prefix + Util.color("&4" + t.getName() + " &ewas reported!");
		} else {
			String error = prefix + Language.INVALID_TARGET;
			p.sendMessage(error);
		}
	}
}