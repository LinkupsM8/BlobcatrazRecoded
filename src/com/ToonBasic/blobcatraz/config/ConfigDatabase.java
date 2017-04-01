package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigDatabase {
    private static final File folder = new File(Core.folder, "users");

    public static YamlConfiguration load(OfflinePlayer op) {
        try {
            File file = file(op);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if(!file.exists()) save(config, file);
            config.load(file);
            defaults(op, config);
            return config;
        } catch(Exception ex) {
            String error = "Failed to load data for " + op.getName() + ":\n" + ex.getMessage();
            Util.print(error);
            return null;
        }
    }

    public static void save(YamlConfiguration config, File file) {
        try {
            if(!file.exists()) {
                folder.mkdirs();
                file.createNewFile();
            }
            config.save(file);
        } catch(Exception ex) {
            String error = "Failed to save data in " + file + ":\n" + ex.getMessage();
            Util.print(error);
        }
    }

    private static void defaults(OfflinePlayer op, YamlConfiguration config) {
        UUID uuid = op.getUniqueId();
        File file = new File(folder, uuid + ".yml");

        set(config, "username", op.getName(), false);
        set(config, "prefix", "&a[&bMember&a] &f", false);
        set(config, "last ip", "localhost", false);
        set(config, "last seen", System.currentTimeMillis(), false);
        set(config, "nickname", op.getName(), false);
        set(config, "balance", 0.00D, false);
        set(config, "tokens", 0, false);
        set(config, "spy", false, false);
        save(config, file);
    }

    private static void set(YamlConfiguration config, String path, Object value, boolean force) {
        boolean b = (config.get(path) == null);
        if(b || force) config.set(path, value);
    }

    private static File file(OfflinePlayer op) {
        UUID uuid = op.getUniqueId();
        String f = uuid + ".yml";
        File file = new File(folder, f);
        return file;
    }

    /*Start Database*/
    public static String nickName(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        String name = config.getString("nickname");
        String nick = Util.color(name + "&r");
        return nick;
    }
    
    public static void nickName(OfflinePlayer op, String nick) {
    	YamlConfiguration config = load(op);
    	set(config, "nickname", nick, true);
    	save(config, file(op));
    }
    
    public static String prefix(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	String prefix = config.getString("prefix");
    	prefix = Util.color(prefix);
    	return prefix;
    }
    
    public static void prefix(OfflinePlayer op, String prefix) {
    	YamlConfiguration config = load(op);
    	set(config, "prefix", prefix, true);
    	save(config, file(op));
    }

    public static int tokens(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        int votes = config.getInt("tokens");
        return votes;
    }
    
    public static boolean hasAccount(OfflinePlayer op) {
    	File file = file(op);
    	return file.exists();
    }
    
    public static double balance(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        double balance = config.getDouble("balance");
        return balance;
    }

    public static void setBalance(OfflinePlayer op, double amount) {
        YamlConfiguration config = load(op);
        set(config, "balance", amount, true);
        save(config, file(op));
    }
    
    public static Map<String, Double> balances() {
    	Map<String, Double> map = Util.newMap();
    	for(OfflinePlayer off : Bukkit.getOfflinePlayers()) {
    		String nam = off.getName();
    		double bal = balance(off);
    		map.put(nam, bal);
    	}
    	return map;
    }

    public static void deposit(OfflinePlayer op, double amount) {
        double balance = balance(op);
        double namount = balance + amount;
        setBalance(op, namount);
    }

    public static void withdraw(OfflinePlayer op, double amount) {
        double namount = (-1 * amount);
        deposit(op, namount);
    }
    
    public static boolean spy(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
        boolean spy = config.getBoolean("spy");
        return spy;
    }
    
    public static void toggleSpy(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        if (spy(op)) {
        	set(config, "spy", false, true);
        } else {
        	set(config, "spy", true, true);
        }
        save(config, file(op));
    }
    
    public static Date lastSeen(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	long time = config.getLong("last seen");
    	Date date = new Date(time);
    	return date;
    }
    
    public static void setLastSeen(OfflinePlayer op, long time) {
    	YamlConfiguration config = load(op);
    	set(config, "last seen", time, true);
    	save(config, file(op));
    }
    
    public static String lastIP(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	String ip = config.getString("last ip");
    	return ip;
    }
    
    public static void setIP(OfflinePlayer op, String ip) {
    	YamlConfiguration config = load(op);
    	set(config, "last ip", ip, true);
    	save(config, file(op));
    }
    
    public static void setHome(OfflinePlayer op, String rawName, Location dest) {
    	YamlConfiguration config = load(op);
    	String name = rawName.toLowerCase();
		set(config, "homes." + name + ".world", dest.getWorld().getName(), true);
		set(config, "homes." + name + ".x", dest.getX(), true);
		set(config, "homes." + name + ".y", dest.getY(), true);
		set(config, "homes." + name + ".z", dest.getZ(), true);
		set(config, "homes." + name + ".yaw", dest.getYaw(), true);
		set(config, "homes." + name + ".pitch", dest.getPitch(), true);
    	save(config, file(op));
    }
    
    public static List<String> getHomes(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
		List<String> list = Util.newList();
		for(String s : config.getConfigurationSection("homes").getKeys(false)) {
			list.add(s);
		}
		Collections.sort(list);
		return list;
	}
    
    public static boolean homeExists(OfflinePlayer op, String rawName) {
    	YamlConfiguration config = load(op);
    	if (!config.contains("homes")) return false;
		return getHomes(op).contains(rawName.toLowerCase());
	}
    
    public static Location getHome(OfflinePlayer op, String rawName) {
    	YamlConfiguration config = load(op);
    	String name = rawName.toLowerCase();
    	return new Location(
    		Bukkit.getWorld(config.getString("homes." + name + ".world")), 
			config.getDouble("homes." + name + ".x"), 
			config.getDouble("homes." + name + ".y"), 
			config.getDouble("homes." + name + ".z"), 
			(float) config.getDouble("homes." + name + ".yaw"), 
			(float) config.getDouble("homes." + name + ".pitch")
		);
    }
    
    public static void delHome(OfflinePlayer op, String rawName) {
    	YamlConfiguration config = load(op);
		config.set("homes." + rawName.toLowerCase(), null);
    	save(config, file(op));
    }
}