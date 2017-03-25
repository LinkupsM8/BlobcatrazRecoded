package com.ToonBasic.blobcatraz.command.staff;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandSeen extends ICommand implements Listener {
	public CommandSeen() {super("seen", "<player>", "blobcatraz.staff.seen", "laston");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		@SuppressWarnings("deprecation")
		OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
		if(ot.isOnline()) {
			Player t = ot.getPlayer();
			cs.sendMessage(Util.color("&a" + t.getName() + " &6is online with IP Address:"));
			cs.sendMessage(t.getAddress().getHostString());
		} else {
			String ip = ConfigDatabase.lastIP(ot);
			Date date = ConfigDatabase.lastSeen(ot);
			SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMMMMMMMM dd, yyyy 'at' hh:mm:ss a z");
			sdf.setTimeZone(TimeZone.getTimeZone("EST"));
			String day = sdf.format(date);
			String[] msg = new String[] {
				Util.color("&a" + ot.getName() + " &6was last seen: "),
				day,
				Util.color("&6with the IP Address:"),
				ip
			};
			cs.sendMessage(msg);
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		InetSocketAddress isa = p.getAddress();
		String ip = isa.getHostString();
		long time = System.currentTimeMillis();
		ConfigDatabase.setIP(p, ip);
		ConfigDatabase.setLastSeen(p, time);
	}
}