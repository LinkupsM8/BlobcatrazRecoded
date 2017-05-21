package com.ToonBasic.blobcatraz.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.ToonBasic.blobcatraz.Core;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class Util {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	private static Core PLUGIN = Core.instance;
	
    public static String prefix = color("&3{&bBlobcatraz&3} &f");
    
    public static void enable() {PLUGIN = Core.instance;}

    public static String color(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
    public static String[] color(String[] msg) {
    	for(int i = msg.length; i > 0; i--) {
    		msg[i - 1] = color(msg[i - 1]);
    	}
    	return msg;
    }
    
    public static String strip(String msg) {return ChatColor.stripColor(msg);}
    
    public static void regEvents(Listener... ls) {
    	for(Listener l : ls) {
    		if(l != null) PM.registerEvents(l, PLUGIN);
    	}
    }
    
    public static void print(Object o) {
        String msg = o.toString();
        Logger log = Bukkit.getLogger();
        log.info(strip(prefix + msg));
    }
    
    public static void broadcast(String... msg) {
    	for(String o : msg) {
    		Bukkit.broadcastMessage(o);
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
		return sb.toString();
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
        int topX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int botX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int topY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int botY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int topZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int botZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
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
    
    public static TextComponent death(Player p, String msg) {
    	String name = p.getName();
    	TextComponent text = new TextComponent(color(name + " &fwas successfully revived by our medics"));
    	BaseComponent[] bc = new ComponentBuilder(msg).create();
    	HoverEvent he = new HoverEvent(Action.SHOW_TEXT, bc);
    	text.setHoverEvent(he);
    	return text;
    }
}