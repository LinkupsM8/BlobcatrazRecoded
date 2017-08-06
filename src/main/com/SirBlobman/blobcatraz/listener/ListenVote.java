package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenVote implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void vote(VotifierEvent e) {
		Vote v = e.getVote();
		String name = v.getUsername();
		String site = v.getServiceName();
		String msg = Util.format(Util.PREFIX + "Thanks to &c%1s&r for voting at &a%2s", name, site);
		Util.broadcast(msg);
		
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		if(op != null) {
			ConfigDatabase.addTokens(op, 1);
			if(op.isOnline()) {
				Player p = op.getPlayer();
				String msg1 = "Thanks for voting, you have received a token!";
				p.sendMessage(msg1);
			}
		}
	}
}