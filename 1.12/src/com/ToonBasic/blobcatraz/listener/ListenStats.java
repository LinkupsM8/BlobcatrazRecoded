package com.ToonBasic.blobcatraz.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;

public class ListenStats implements Listener {
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		Player p = e.getEntity();
		ConfigDatabase.addDeath(p);
		ConfigDatabase.setKillStreak(p, 0);
		LivingEntity le = p.getKiller();
		if(le instanceof Player) {
			Player k = (Player) le;
			ConfigDatabase.addKill(k);
			int streak = ConfigDatabase.killStreak(k) + 1;
			ConfigDatabase.setKillStreak(k, streak);
		}
	}
}