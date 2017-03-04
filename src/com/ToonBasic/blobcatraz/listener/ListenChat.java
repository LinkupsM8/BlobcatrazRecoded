package com.ToonBasic.blobcatraz.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.utility.Util;

public class ListenChat implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e) {
		String format = Util.color("&f%1s &7>>&f %2s");
		e.setFormat(format);
	}
}