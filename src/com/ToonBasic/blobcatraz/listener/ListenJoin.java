package com.ToonBasic.blobcatraz.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ToonBasic.blobcatraz.utility.Util;

public class ListenJoin implements Listener {
	@EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        boolean noob = p.hasPlayedBefore();
        if(noob) {
        	String msg = Util.prefix + Util.color("&dPlease welcome &f" + name + " &dto &3&lBlobcatraz&d!");
        	Util.broadcast(msg);
        } else {
        	String msg = Util.prefix + Util.color("&dWelcome back \u263b");
        	p.sendMessage(msg);
        }
    }
}
