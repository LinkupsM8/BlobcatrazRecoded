package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ToonBasic.blobcatraz.utility.Util;

import net.md_5.bungee.api.chat.TextComponent;

public class ListenDeath implements Listener {
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		String msg = e.getDeathMessage();
		TextComponent tc = Util.death(p, msg);
		e.setDeathMessage(null);
		for(Player o : Bukkit.getOnlinePlayers()) {
			Spigot s = o.spigot();
			s.sendMessage(tc);
		}
	}
}