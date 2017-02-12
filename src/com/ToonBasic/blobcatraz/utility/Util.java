package com.ToonBasic.blobcatraz.utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Util {
    public static String prefix = color("&3{&bBlobcatraz&3} &f");

    public static String color(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
    public static String strip(String msg) {return ChatColor.stripColor(msg);}

    public static void print(Object o) {
        String msg = o.toString();
        Logger log = Bukkit.getLogger();
        log.info(strip(prefix + msg));
    }
    
	@SafeVarargs
	public static <T> List<T> newList(T... ts) {
		List<T> list = new ArrayList<T>();
		if(ts.length > 0) {for(T t : ts) list.add(t);}
		return list;
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
    
    public static String money(double amount) {
    	DecimalFormat df = new DecimalFormat("#.##");
    	df.setDecimalSeparatorAlwaysShown(true);
    	String money = "$" + df.format(amount);
    	return money;
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
}