package com.ToonBasic.blobcatraz.command.staff;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		String target = args[0];
		OfflinePlayer ot = Bukkit.getOfflinePlayer(target);
		if(ot.isOnline()) {
			Player t = ot.getPlayer();
			cs.sendMessage(Util.color("&a" + t.getName() + " &6is online with IP Address:"));
			cs.sendMessage(t.getAddress().getHostString());
		} else {
			Date date = ConfigDatabase.lastSeen(ot);
			if(date == null) {
				String msg = prefix + "That player was never online!";
				cs.sendMessage(msg);
			} else {
				String ip = ConfigDatabase.lastIP(ot);
				Location l = ConfigDatabase.lastLocation(ot);
				SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMMMMMMMM dd, yyyy 'at' hh:mm:ss a z");
				sdf.setTimeZone(TimeZone.getTimeZone("EST"));
				String day = sdf.format(date);
				String[] msg = new String[] {
					Util.color("&a" + ot.getName() + " &6was last seen on: "),
					day,
					Util.color("&6with the IP Address:"),
					ip,
					Util.color("&6at this Location:"),
					locationToString(l)
				};
				cs.sendMessage(msg);
			}
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		InetSocketAddress isa = p.getAddress();
		String ip = isa.getHostString();
		long time = System.currentTimeMillis();
		Location l = p.getLocation();
		ConfigDatabase.setIP(p, ip);
		ConfigDatabase.setLastSeen(p, time);
		ConfigDatabase.setLocation(p, l);
	}
	
	private String locationToString(Location l) {
		if(l == null) {
			return "null";
		} else {
			World w = l.getWorld();
			String world = w.getName();
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			String loc = world + ": " + x + ", " + y + ", " + z;
			return loc;
		}
	}
}