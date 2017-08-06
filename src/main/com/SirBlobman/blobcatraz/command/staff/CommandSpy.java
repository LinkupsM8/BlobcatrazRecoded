package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSpy extends PlayerCommand implements Listener {
	public CommandSpy() {super("spy", "", "blobcatraz.staff.spy", "commandspy", "socialspy");}
	
	@Override
	public void run(Player p, String[] args) {
		ConfigDatabase.toggleSpy(p);
		boolean spy = ConfigDatabase.spy(p);
		String str = str(spy);
		String msg = Util.format(prefix + "&aCommandSpy is now %1s", str);
		p.sendMessage(msg);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void cmd(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String n = p.getName();
		String m = e.getMessage();
		for(Player o : Bukkit.getOnlinePlayers()) {
			String on = o.getName();
			boolean b = !on.equals(n);
			boolean s = ConfigDatabase.spy(o);
			if(b && s) {
				String msg = Util.format("&2<Spy: &a%1s> &7&o%2s", n, m);
				o.sendMessage(msg);
			}
		}
	}
	
	private String str(Object o) {
		if(o instanceof Boolean) {
			boolean b = (boolean) o;
			String s = b ? "on" : "off";
			return s;
		} else return Util.str(o);
	}
}