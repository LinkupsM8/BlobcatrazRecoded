package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class ListenVote implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void vote(VotifierEvent e) {
		Vote v = e.getVote();
		String voter = v.getUsername();
		String site = v.getServiceName();
		String msg = Util.color(Util.prefix + "Thanks to &c" + voter + "&f for voting at &a" + site);
		Util.broadcast(msg);
		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(voter);
		if(op != null) {
			ConfigDatabase.addTokens(op, 1);
			if(op.isOnline()) {
				Player p = op.getPlayer();
				p.sendMessage("Thank you for voting, you have received a token!");
			}
		}
	}
}