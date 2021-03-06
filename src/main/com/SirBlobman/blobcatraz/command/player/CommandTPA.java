package com.SirBlobman.blobcatraz.command.player;

import static com.SirBlobman.blobcatraz.command.player.CommandTPChoose.TP;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.special.TPRequest;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandTPA extends PlayerCommand {
	public CommandTPA() {super("tpa", "<player>", "blobcatraz.player.tpa", "tpask");}
	
	@Override
	public void run(Player p, String[] args) {
		String pname = p.getName();
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String tname = t.getName();
			TPRequest req = new TPRequest(p, p, t);
			TP.put(t, req);
			String[] msg1 = Util.color(
				prefix + "&a" + pname + " &ewants to TP to you",
				"&eTo allow them, type &a/tpaccept",
				"&eTo deny them, type &a/tpdeny"
			);
			String msg2 = Util.format(prefix + "&eYou asked &a%1s &eto TP to you.", tname);
			t.sendMessage(msg1);
			p.sendMessage(msg2);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
}