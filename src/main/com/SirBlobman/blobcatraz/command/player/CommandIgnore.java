package com.SirBlobman.blobcatraz.command.player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandIgnore extends PlayerCommand implements Listener {
	private static Map<Player, List<Player>> IGNORE = Util.newMap();
	public CommandIgnore() {super("ignore", "<player>", "blobcatraz.player.ignore");}
	
	@Override
	public void run(Player p, String[] args) {
		List<Player> list = Util.newList();
		if(!IGNORE.containsKey(p)) IGNORE.put(p, list);
		list = IGNORE.get(p);
		
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			list.add(t);
			IGNORE.put(p, list);
			String name = t.getName();
			String msg = Util.format(prefix + "&eYou are now ignoring &a%1s", name);
			p.sendMessage(msg);
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		Set<Player> set = e.getRecipients();
		for(Player o : set) {
			if(IGNORE.containsKey(o)) {
				List<Player> list = IGNORE.get(o);
				if(list.contains(p)) set.remove(o);
				else continue;
			}
		}
	}
}