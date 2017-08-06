package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.special.TPRequest;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandTPChoose extends PlayerCommand {
	public static Map<Player, TPRequest> TP = Util.newMap();
	public CommandTPChoose() {super("tpchoose", "[deny/accept]", "blobcatraz.player.tpa", "tpaccept", "tpdeny");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = getCommandUsed().toLowerCase().substring(2);
		if(TP.containsKey(p)) {
			TPRequest tp = TP.get(p);
			Player s = tp.sender();
			if(args.length > 0) sub = args[0].toLowerCase();
			if(sub.equals("accept")) {
				Player w = tp.who();
				Location l = tp.location();
				String msg1 = "Request accepted!";
				String msg2 = Util.format(prefix + "&a%1s&e has accepted your request!", p.getName());
				String msg3 = "Teleporting...";
				p.sendMessage(msg1);
				s.sendMessage(msg2);
				w.sendMessage(msg3);
				w.teleport(l);
				TP.remove(p);
			} else if(sub.equals("deny")) {
				String msg1 = "Request denied!";
				String msg2 = Util.format(prefix + "&c%1s&e has denied your request", p.getName());
				p.sendMessage(msg1);
				s.sendMessage(msg2);
				TP.remove(p);
			} else {
				String error = getFormattedUsage(getCommandUsed());
				p.sendMessage(error);
			}
		} else {
			String error = "You do not have any pending requests!";
			p.sendMessage(error);
		}
	}
}