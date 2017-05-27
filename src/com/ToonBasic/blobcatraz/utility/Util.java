package com.ToonBasic.blobcatraz.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.ToonBasic.blobcatraz.Core;

public class Util {
	private static final Server SERVER = Bukkit.getServer();
	private static final ConsoleCommandSender CONSOLE = SERVER.getConsoleSender();
	private static final PluginManager PM = SERVER.getPluginManager();
	private static Core PLUGIN = Core.instance;
	
    public static String prefix = color("&3{&bBlobcatraz&3} &f");
    
    public static void enable() {PLUGIN = Core.instance;}

    public static String strip(String msg) {return ChatColor.stripColor(msg);}
    public static String color(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
    public static String[] color(String... msg) {
    	for(int i = msg.length; i > 0; i--) {
    		msg[i - 1] = color(msg[i - 1]);
    	}
    	return msg;
    }
    
    public static void print(Object... os) {
    	for(Object o : os) {
    		if(o != null) {
    			String msg = color(o.toString());
    			CONSOLE.sendMessage(msg);
    		}
    	}
    }
    
    public static void broadcast(Object... os) {
    	print(os);
    	for(Player p : SERVER.getOnlinePlayers()) {
    		for(Object o : os) {
    			if(o != null) {
    				String msg = color(o.toString());
    				p.sendMessage(msg);
    			}
    		}
    	}
    }
    
    public static void regEvents(Listener... ls) {
    	for(Listener l : ls) {
    		if(l != null) PM.registerEvents(l, PLUGIN);
    	}
    }
    
    @SafeVarargs
	public static <T> T[] newArray(T... ts) {
    	T[] t = ts;
    	return t;
    }
    
	@SafeVarargs
	public static <T> List<T> newList(T... ts) {
		List<T> list = new ArrayList<T>();
		if(ts.length > 0) {for(T t : ts) list.add(t);}
		return list;
	}
	
	public static <T> List<T> newList(Collection<T> co) {
		List<T> list = newList();
		for(T t : co) list.add(t);
		return list;
	}
	
	public static String formatList(char type, List<String> list) {
		if(list == null || list.isEmpty()) return "[]";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if(i != 0) sb.append("\n");
			String val = " " + list.get(i);
			sb.append(type + val);
		}
		return color(sb.toString());
	}
	
	public static String listToString(List<String> list, String split) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if(i != 0) sb.append(color(split));
			String s = list.get(i);
			sb.append(color("&2" + s));
		}
		String ss = sb.toString();
		return ss;
	}
	
	public static <K, V> Map<K, V> newMap() {
		Map<K, V> map = new HashMap<K, V>();
		return map;
	}

    public static String finalArgs(int start, String... args) {
        StringBuilder sb = new StringBuilder();
        for(int i = start; i < args.length; i++) {
            if(i != start) sb.append(" ");
            sb.append(args[i]);
        }
        return sb.toString();
    }
    
    public static String[] subArgs(int start, String... args) {
    	String f = finalArgs(start, args);
    	String[] ss = f.split(" ");
    	return ss;
    }

    public static List<String> matching(List<String> o, String mat) {
        if(o == null || mat == null) return Collections.emptyList();
        List<String> match = Collections.emptyList();
        for(String s : o) {
            String l = s.toLowerCase();
            String a = mat.toLowerCase();
            if(l.startsWith(a)) match.add(s);
        }
        return match;
    }

    public static String json(String o) {
        String json = "{\"text\": \"" + o + "\"}";
        return json;
    }

    public static List<Block> blocks(Location l1, Location l2) {
        List<Block> list = Collections.emptyList();
        int x1 = l1.getBlockX(), x2 = l2.getBlockX();
        int y1 = l1.getBlockY(), y2 = l2.getBlockY();
        int z1 = l1.getBlockZ(), z2 = l2.getBlockZ();
        
        int topX = (x1 < x2 ? x2 : x1), botX = (x1 > x2 ? x2 : x1);
        int topY = (y1 < y2 ? y2 : y1), botY = (y1 > y2 ? y2 : y1);
        int topZ = (z1 < z2 ? z2 : z1), botZ = (z1 > z2 ? z2 : z1);
        
        for(int x = botX; x <= topX; x++) {
            for(int z = botZ; z <= topZ; z++) {
                for(int y = botY; y <= topY; y++) {
                    World w = l1.getWorld();
                    Block b = w.getBlockAt(x, y, z);
                    list.add(b);
                }
            }
        }
        return list;
    }
    
    public static String toString(Object o) {
    	if(o instanceof Boolean) {
    		boolean b = (boolean) o;
    		return b ? "yes" : "no";
    	} else if(o instanceof GameMode) {
    		GameMode gm = (GameMode) o;
    		String name = gm.name();
    		return WordUtil.capitalize(name);
    	} else return o.toString();
    }
}