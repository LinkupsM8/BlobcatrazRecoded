package com.SirBlobman.blobcatraz.command.staff;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSeen extends ICommand implements Listener {
	public CommandSeen() {super("seen", "<player>", "blobcatraz.staff.seen", "laston");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
		if(ot == null) {
			String error = "That player was never online!";
			cs.sendMessage(error);
		} else {
			if(ot.isOnline()) {
				Player t = ot.getPlayer();
				String name = t.getName();
				InetSocketAddress isa = t.getAddress();
				String ip = Util.str(isa);
				String[] msg = Util.color(
					"&a" + name + " &6is online with IP Address:",
					"&f" + ip
				);
				cs.sendMessage(msg);
			} else {
				Date d = ConfigDatabase.lastSeen(ot);
				if(d != null) {
					String name = ot.getName();
					String ip = ConfigDatabase.lastIP(ot);
					Location l = ConfigDatabase.lastLocation(ot);
					String f = "MMMMMMMM dd, yyyy 'at' hh:mm:ss a z";
					SimpleDateFormat sdf = new SimpleDateFormat(f);
					String day = sdf.format(d);
					String[] msg = Util.color(
						"&a" + name + " &6was last seen on:",
						"&f" + day,
						"&6with the IP Address:",
						"&f" + ip,
						"&6at this Location:",
						"&f" + Util.str(l)
					);
					cs.sendMessage(msg);
				} else {
					String error = "That player was never online!";
					cs.sendMessage(error);
				}
			}
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		InetSocketAddress isa = p.getAddress();
		
		String ip = Util.str(isa);
		long time = System.currentTimeMillis();
		Location l = p.getLocation();
		
		ConfigDatabase.setLastIP(p, ip);
		ConfigDatabase.setLastSeen(p, time);
		ConfigDatabase.setLastLocation(p, l);
	}
}