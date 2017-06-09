package com.ToonBasic.blobcatraz.command.player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandIgnore extends ICommand implements Listener {
	private static Map<Player, List<Player>> ignore = Util.newMap();
	public CommandIgnore() {super("ignore", "<player>", "blobcatraz.player.ignore");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		List<Player> list = Util.newList();
		if(!ignore.containsKey(p)) ignore.put(p, list);
		list = ignore.get(p);
		
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			list.add(t);
			ignore.put(p, list);
			String msg = prefix + Util.color("&eYou are now ignoring &a" + t.getName());
			p.sendMessage(msg);
		} else {
			String error = prefix + Language.INVALID_TARGET;
			p.sendMessage(error);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		Set<Player> reps = e.getRecipients();
		for(Player o : reps) {
			if(ignore.containsKey(o)) {
				List<Player> ig = ignore.get(o);
				if(ig.contains(p)) reps.remove(o);
			}
		}
	}
}