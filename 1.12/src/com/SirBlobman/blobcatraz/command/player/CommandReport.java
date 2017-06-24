package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandReport extends PlayerCommand {
	public CommandReport() {super("report", "<player> <reason>", "blobcatraz.player.report");}
	
	@Override
	public void run(Player p, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String tname = t.getName();
			String reason = Util.finalArgs(1, args);
			String trim = reason.trim();
			String c = Util.color(trim);
			for(Player o : Bukkit.getOnlinePlayers()) {
				String perm = "blobcatraz.report.see";
				if(o.hasPermission(perm)) {
					String pname = o.getName();
					String rep = Util.format(prefix + "&4%1s &7was reported by &2%2s for:\n%3s", tname, pname, c);
					o.sendMessage(rep);
				}
			}
			
			String msg = Util.format(prefix + "&4%1s &ewas reported to any online staff members!", tname);
			p.sendMessage(msg);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
}