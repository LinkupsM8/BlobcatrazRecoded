package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenChat implements Listener {
	@EventHandler(priority=EventPriority.HIGH)
	public void chat(AsyncPlayerChatEvent e) {
		String format = Util.color("&f%1s &7>>&f %2s");
		e.setFormat(format);
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if(p.hasPermission("blobcatraz.chat.color")) {
			String cmsg = Util.color(msg);
			e.setMessage(cmsg);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void ping(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String pname = p.getName();
		String msg = e.getMessage();
		for(Player on : Bukkit.getOnlinePlayers()) {
			String oname = on.getName();
			if(!oname.equals(pname)) {
				String lmsg = msg.toLowerCase();
				String loname = oname.toLowerCase();
				if(lmsg.contains(loname)) {
					PlayerUtil.ping(on);
				}
			}
		}
	}
}