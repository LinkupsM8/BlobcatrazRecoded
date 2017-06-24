package com.SirBlobman.blobcatraz.utility;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.command.player.CommandEmojis;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.net.InetSocketAddress;
import java.util.*;

import net.md_5.bungee.api.ChatColor;

public class Util {
	public static final Server SERVER = Bukkit.getServer();
	public static final ConsoleCommandSender CONSOLE = SERVER.getConsoleSender();
	public static final PluginManager PM = SERVER.getPluginManager();
	private static final Core PLUGIN = Core.INSTANCE;
	
	public static final String PREFIX = color("&3{&bBlobcatraz&3} &f");
	
	public static String color(String o) {return ChatColor.translateAlternateColorCodes('&', o);}
	public static String strip(String c) {return ChatColor.stripColor(c);}
	public static String[] color(String... ss) {
		int length = (ss.length - 1);
		for(int i = length; i > -1; i--) {
			String c = color(ss[i]);
			ss[i] = c;
		}
		return ss;
	}
	
	public static String format(Object o, Object... os) {
		String s = str(o);
		String e = CommandEmojis.format(s);
		String f = String.format(e, os);
		String c = color(f);
		return c;
	}
	
	public static String str(Object o) {
		if(o == null) return "";
		if((o instanceof Short) || (o instanceof Integer) || (o instanceof Long)) {
			Number n = (Number) o;
			long l = n.longValue();
			return Long.toString(l);
		} else if((o instanceof Float) || (o instanceof Double) || (o instanceof Number)) {
			Number n = (Number) o;
			double d = n.doubleValue();
			return Double.toString(d);
		} else if(o instanceof Boolean) {
			boolean b = (boolean) o;
			String s = b ? "yes" : "no";
			return s;
		} else if(o instanceof GameMode) {
			GameMode gm = (GameMode) o;
			String name = gm.name();
			String r = WordUtil.capitalize(name);
			return r;
		} else if(o instanceof Location) {
			Location l = (Location) o;
			World w = l.getWorld();
			String name = w.getName();
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			String ya = NumberUtil.cropDecimal(l.getYaw(), 2);
			String pi = NumberUtil.cropDecimal(l.getPitch(), 2);
			
			String loc = format("%1s: X: %2s, Y: %3s, Z: %4s, Yaw: %5s, Pitch: %6s", name, x, y, z, ya, pi);
			return loc;
		} else if(o instanceof InetSocketAddress){
			InetSocketAddress isa = (InetSocketAddress) o;
			String host = isa.getHostString();
			return host;
		} else if(o instanceof String) {
			String s = (String) o;
			return s;
		} else {
			String s = o.toString();
			return s;
		}
	}
	
	public static void print(Object... os) {
		for(Object o : os) {
			String s = str(o);
			String msg = color(s);
			CONSOLE.sendMessage(msg);
		}
	}
	
	public static void broadcast(Object... os) {
		print(os);
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Object o : os) {
				String s = str(o);
				String msg = color(s);
				p.sendMessage(msg);
			}
		}
	}
	
	public static void regEvents(Listener... ls) {
		for(Listener l : ls) {
			if(l != null) PM.registerEvents(l, PLUGIN);
		}
	}
	
	public static void regEvents(Plugin pl, Listener... ls) {
		for(Listener l : ls) {
			if(l != null) PM.registerEvents(l, pl);
		}
	}
	
	public static void callEvent(Event e) {PM.callEvent(e);}
	
	@SafeVarargs
	public static <O> List<O> newList(O... os) {
		List<O> list = new ArrayList<O>();
		for(O o : os) {
			boolean n = (o != null);
			if(n) list.add(o);
		}
		return list;
	}
	
	public static String joinList(List<String> list, String join) {
		StringBuilder sb = new StringBuilder();
		for(String s : list) {
			sb.append(join);
			sb.append(s);
		}
		String ss = sb.toString();
		return ss;
	}
	
	public static <O> List<O> newList(Collection<O> os) {
		List<O> list = newList();
		list.addAll(os);
		return list;
	}
	
	public static <K, V> Map<K, V> newMap() {
		Map<K, V> map = new HashMap<K, V>();
		return map;
	}
	
	public static String finalArgs(int s, String... args) {
		StringBuilder sb = new StringBuilder();
		for(int i = s; i < args.length; i++) {
			if(i != s) sb.append(" ");
			String arg = args[i];
			sb.append(arg);
		}
		String ss = sb.toString();
		return ss;
	}
	
	public static String[] subArgs(int s, String... args) {
		String f = finalArgs(s, args);
		String[] ss = f.split(" ");
		return ss;
	}
	
	public static List<String> matching(List<String> o, String match) {
		if(o == null || match == null) return newList();
		List<String> list = newList();
		for(String s : o) {
			String l = s.toLowerCase();
			String a = match.toLowerCase();
			if(l.startsWith(a)) list.add(s);
		}
		return list;
	}
	
	public static String toJSON(String o) {
		String json = "{\"text\": \"" + o + "\"}";
		return json;
	}
	
	public static List<Block> blocks(Location l1, Location l2) {
		List<Block> list = newList();
		int x1 = l1.getBlockX(), x2 = l2.getBlockX(),
		y1 = l1.getBlockY(), y2 = l2.getBlockY(),
		z1 = l1.getBlockZ(), z2 = l2.getBlockZ();
		
		int topX = Math.max(x1, x2), botX = Math.min(x1, x2),
		topY = Math.max(y1, y2), botY = Math.min(y1, y2),
		topZ = Math.max(z1, z2), botZ = Math.min(z1, z2);
		
		World w = l1.getWorld();
		for(int x = botX; x <= topX; x++) {
			for(int z = botZ; z <= topZ; z++) {
				for(int y = botY; y <= topY; y++) {
					Block b = w.getBlockAt(x, y, z);
					list.add(b);
				}
			}
		}
		return list;
	}
}