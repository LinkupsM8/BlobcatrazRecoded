package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ScoreboardUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandAFK extends ICommand implements Listener {
	public CommandAFK() {super("afk", "[reason]", "blobcatraz.player.afk", "awayfromkeyboard");}
	private static List<Player> afk = Util.newList();
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String reason = Util.finalArgs(0, args);
		reason = Util.color(reason);
		Player p = (Player) cs;
		String msg = Util.color("&6 * &7" + p.getName() + " &eis now AFK\n  &f" + reason);
		afk.add(p);
		ScoreboardUtil.afk(p);
		Bukkit.broadcastMessage(msg);
	}
	
	@EventHandler
	private void afk(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		un(p);
	}
	
	@EventHandler
	private void afk(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		un(p);
	}
	
	private void un(Player p) {
		if(afk.contains(p)) {
			afk.remove(p);
			ScoreboardUtil.unAFK(p);
			String msg = Util.color("&6 * &7" + p.getName() + " &eis no longer AFK");
			Bukkit.broadcastMessage(msg);
		}
	}
}