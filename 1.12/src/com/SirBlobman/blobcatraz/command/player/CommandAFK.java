package com.SirBlobman.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ScoreboardUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandAFK extends PlayerCommand implements TabCompleter, Listener {
	private static List<Player> afk = Util.newList();
	public CommandAFK() {super("afk", "[reason]", "blobcatraz.player.afk", "awayfromkeyboard");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = p.getName();
		if(afk.contains(p)) un(p);
		else {
			String reason = Util.finalArgs(0, args);
			boolean n = ((reason == null) || (reason.equals("")));
			String msg1 = Util.color("&6 * &7" + name + " &eis now AFK");
			String msg2 = n ? msg1 : Util.color(msg1 + "\n&f- " + reason);
			p.setVelocity(new Vector(0, 0, 0));
			afk.add(p);
			ScoreboardUtil.afk(p);
			Util.broadcast(msg2);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args) {
		if(args.length > 0) {
			List<String> reasons = Util.newList("dinner", "shower", "bathroom", "no reason");
			String arg = args[0];
			List<String> match = Util.matching(reasons, arg);
			return match;
		} else return null;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void afk(PlayerMoveEvent e) {
		if(!e.isCancelled()) {
			Player p = e.getPlayer();
			un(p);
		}
	}
	
	@EventHandler
	public void afk(AsyncPlayerChatEvent e) {
		if(!e.isCancelled()) {
			Player p = e.getPlayer();
			un(p);
		}
	}
	
	private void un(Player p) {
		String name = p.getName();
		boolean is = afk.contains(p);
		if(is) {
			afk.remove(p);
			ScoreboardUtil.unAFK(p);
			String msg = "&6 * &7" + name + " &eis no longer AFK";
			Util.broadcast(msg);
		}
	}
}