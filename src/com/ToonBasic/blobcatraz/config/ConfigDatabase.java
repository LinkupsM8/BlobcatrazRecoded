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
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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
        set(config, "prefix", "&a[&bA&a] &f", false);
        set(config, "last ip", "localhost", false);
        set(config, "last seen", 0, false);
        set(config, "last location", null, false);
        set(config, "nickname", op.getName(), false);
        set(config, "balance", 0.00D, false);
        set(config, "tokens", 0, false);
        set(config, "warnings", Util.newList(), false);
        set(config, "spy", false, false);
        set(config, "stats.deaths", 0, false);
        set(config, "stats.kills", 0, false);
        set(config, "stats.kill streak", 0, false);
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

    public static void addTokens(OfflinePlayer op, int amount) {
    	YamlConfiguration config = load(op);
    	int tokens = tokens(op);
    	tokens = tokens + amount;
    	set(config, "tokens", tokens, true);
    	save(config, file(op));
    }
    
    public static int tokens(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        int votes = config.getInt("tokens");
        return votes;
    }
    
    public static List<String> warnings(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	List<String> list = config.getStringList("warnings");
    	return list;
    }
    
    public static void addWarning(OfflinePlayer op, String reason) {
    	YamlConfiguration config = load(op);
    	List<String> list = warnings(op);
    	list.add(reason);
    	set(config, "warnings", list, true);
    	save(config, file(op));
    }
    
    public static void clearWarnings(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	List<String> list = Util.newList();
    	set(config, "warnings", list, true);
    	save(config, file(op));
    }
    
    public static boolean staff(OfflinePlayer op) {
    	boolean staff = op.isOp();
    	return staff;
    }
    
    public static boolean hasAccount(OfflinePlayer op) {
    	if(op == null) return false;
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
    		if(!staff(off)) {
        		String nam = off.getName();
        		double bal = balance(off);
        		map.put(nam, bal);
    		}
    	}
    	return map;
    }

    public static void deposit(OfflinePlayer op, double amount) {
        double balance = balance(op);
        double namount = balance + amount;
        setBalance(op, namount);
    }

    public static void withdraw(OfflinePlayer op, double amount) {
        double balance = balance(op);
        double namount = balance - amount;
        setBalance(op, namount);
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
    	if(time == 0) return null;
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
    
    public static Location lastLocation(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	Location l = (Location) config.get("last location");
    	return l;
    }

    public static void setLocation(OfflinePlayer op, Location l) {
    	YamlConfiguration config = load(op);
    	set(config, "last location", l, true);
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
    
    //Stats
    public static void addDeath(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	int deaths = config.getInt("stats.deaths") + 1;
    	set(config, "stats.deaths", deaths, true);
    	save(config, file(op));
    }
    
    public static void addKill(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	int kills = config.getInt("stats.kills") + 1;
    	set(config, "stats.kills", kills, true);
    	save(config, file(op));
    }
    
    public static void setKillStreak(OfflinePlayer op, int streak) {
    	YamlConfiguration config = load(op);
    	set(config, "stats.kill streak", streak, true);
    	save(config, file(op));
    }
    
    public static int kills(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	int s = config.getInt("stats.kills");
    	return s;
    }
    
    public static int deaths(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	int s = config.getInt("stats.deaths");
    	return s;
    }
    
    public static int killStreak(OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	int s = config.getInt("stats.kill streak");
    	return s;
    }
    
    public static void setInventory(World w, OfflinePlayer op, List<ItemStack> items) {
    	YamlConfiguration config = load(op);
    	String name = w.getName();
    	String path = "inventory." + name;
    	set(config, path, items, true);
    	save(config, file(op));
    }
    
    @SuppressWarnings("unchecked")
	public static List<ItemStack> getInventory(World w, OfflinePlayer op) {
    	YamlConfiguration config = load(op);
    	String name = w.getName();
    	String path = "inventory." + name;
    	List<ItemStack> list = (List<ItemStack>) config.get(path);
    	if(list == null) list = Util.newList();
    	return list;
    }
}