package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigDatabase extends Config {
	private static final File USERS = new File(FOLDER, "users");
	
	private static File file(OfflinePlayer op) {
		UUID uuid = op.getUniqueId();
		String id = uuid.toString();
		String f = id + ".yml";
		File file = new File(USERS, f);
		return file;
	}
	
	public static YamlConfiguration load(OfflinePlayer op) {
		try {
			File file = file(op);
			YamlConfiguration config = load(file);
			defaults(op, config);
			return config;
		} catch(Throwable ex) {
			String name = op.getName();
			String error = "Failed to load data for " + name + ":";
			Util.print(error, ex.getMessage());
			return null;
		}
	}
	
	public static void save(YamlConfiguration config, File file) {
		try {Config.save(config, file);} 
		catch(Exception ex) {
			String error = "Failed to save data in " + file + ":";
			Util.print(error, ex.getMessage());
		}
	}
	
	private static void defaults(OfflinePlayer op, YamlConfiguration config) {
		String username = op.getName();
		String prefix = "&a[&bA&a] &f";
		String lastip = "localhost";
		long lastseen = 0;
		double balance = 0.0D;
		int tokens = 0;
		List<String> warnings = Util.newList();
		boolean spy = false;
		int deaths = 0;
		int kills = 0;
		int killstreak = 0;
		
		set(config, "username", username, true);
		set(config, "prefix", prefix, false);
		set(config, "last.ip", lastip, false);
		set(config, "last.seen", lastseen, false);
		set(config, "last.location", null, false);
		set(config, "nickname", username, false);
		set(config, "balance", balance, false);
		set(config, "tokens", tokens, false);
		set(config, "warnings", warnings, false);
		set(config, "spy", spy, false);
		set(config, "stats.deaths", deaths, false);
		set(config, "stats.kills", kills, false);
		set(config, "stats.kill streak", killstreak, false);
		File file = file(op);
		save(config, file);
	}
	
	private static void set(YamlConfiguration config, String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(n || force) config.set(path, value);
	}
	
	public static String nick(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		String name = config.getString("nickname");
		String nick = Util.format(name);
		return nick;
	}
	
	public static void setNick(OfflinePlayer op, String nick) {
		YamlConfiguration config = load(op);
		nick = nick.replace('\u00a7', '&');
		set(config, "nickname", nick, true);
		save(config, file(op));
	}
	
	public static String prefix(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		String pre = config.getString("prefix");
		String fix = Util.format(pre);
		return fix;
	}
	
	public static void setPrefix(OfflinePlayer op, String prefix) {
		YamlConfiguration config = load(op);
		prefix = prefix.replace('\u00a7', '&');
		set(config, "prefix", prefix, true);
		save(config, file(op));
	}
	
	public static int tokens(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		int t = config.getInt("tokens");
		return t;
	}
	
	public static void addTokens(OfflinePlayer op, int amount) {
		YamlConfiguration config = load(op);
		int t = tokens(op);
		int n = t + amount;
		set(config, "tokens", n, true);
		save(config, file(op));
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
		boolean is = op.isOp();
		return is;
	}
	
	public static boolean exists(OfflinePlayer op) {
		if(op == null) return false;
		File file = file(op);
		boolean e = file.exists();
		return e;
	}
	
	public static double balance(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		double bal = config.getDouble("balance");
		return bal;
	}
	
	public static void setBalance(OfflinePlayer op, double amount) {
		YamlConfiguration config = load(op);
		set(config, "balance", amount, true);
		save(config, file(op));
	}
	
	public static void deposit(OfflinePlayer op, double amount) {
		amount = Math.abs(amount);
		double bal = balance(op);
		double n = bal + amount;
		setBalance(op, n);
	}
	
	public static void withdraw(OfflinePlayer op, double amount) {
		amount = Math.abs(amount);
		double bal = balance(op);
		double n = bal - amount;
		setBalance(op, n);
	}
	
	public static Map<String, Double> balances() {
		Map<String, Double> map = Util.newMap();
		for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
			boolean s = staff(op);
			if(!s) {
				String name = op.getName();
				double bal = balance(op);
				map.put(name, bal);
			}
		}
		return map;
	}
	
	public static boolean spy(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		boolean spy = config.getBoolean("spy");
		return spy;
	}
	
	public static void toggleSpy(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		boolean spy = spy(op);
		set(config, "spy", !spy, true);
		save(config, file(op));
	}
	
	public static Date lastSeen(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		long time = config.getLong("last.seen");
		if(time == 0) return null;
		Date d = new Date(time);
		return d;
	}
	
	public static void setLastSeen(OfflinePlayer op, long time) {
		YamlConfiguration config = load(op);
		set(config, "last.seen", time, true);
		save(config, file(op));
	}
	
	public static String lastIP(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		String ip = config.getString("last.ip");
		return ip;
	}
	
	public static void setLastIP(OfflinePlayer op, String ip) {
		YamlConfiguration config = load(op);
		set(config, "last.ip", ip, true);
		save(config, file(op));
	}
	
	public static Location lastLocation(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		Object o = config.get("last.location");
		if(o instanceof Location) {
			Location l = (Location) o;
			return l;
		} else return null;
	}
	
	public static void setLastLocation(OfflinePlayer op, Location l) {
		YamlConfiguration config = load(op);
		set(config, "last.location", l, true);
		save(config, file(op));
	}
	
	public static void setHome(OfflinePlayer op, String name, Location l) {
		YamlConfiguration config = load(op);
		String lo = name.toLowerCase();
		String path = "homes." + lo;
		set(config, path, l, true);
		save(config, file(op));
	}
	
	public static List<String> homes(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		ConfigurationSection cs = config.getConfigurationSection("homes");
		if(cs != null) {
			Set<String> keys = cs.getKeys(false);
			
			List<String> list = Util.newList();
			for(String s : keys) {list.add(s);}
			Collections.sort(list);
			return list;
		} else return Util.newList();
	}
	
	public static boolean homeExists(OfflinePlayer op, String name) {
		String lo = name.toLowerCase();
		YamlConfiguration config = load(op);
		boolean homes = config.contains("homes");
		if(!homes) return false;
		else {
			List<String> list = homes(op);
			boolean e = list.contains(lo);
			return e;
		}
	}
	
	public static Location home(OfflinePlayer op, String name) {
		String lo = name.toLowerCase();
		YamlConfiguration config = load(op);
		String path = "homes." + lo;
		Object o = config.get(path);
		if(o instanceof Location) {
			Location l = (Location) o;
			return l;
		} else return null;
	}
	
	public static void delHome(OfflinePlayer op, String name) {
		String lo = name.toLowerCase();
		YamlConfiguration config = load(op);
		String path = "homes." + lo;
		set(config, path, null, true);
		save(config, file(op));
	}

	@SuppressWarnings("unchecked")
	public static List<ItemStack> getInventory(World w, OfflinePlayer op) {
		YamlConfiguration config = load(op);
		String name = w.getName();
		if(name.equals("Mines") || name.contains("SkyBlock")) {name = "SkyBlock";}
		String path = "inventory." + name;
		List<ItemStack> list = (List<ItemStack>) config.getList(path);
		if(list == null) list = Util.newList();
		return list;
	}
	
	public static void setInventory(World w, OfflinePlayer op, List<ItemStack> list) {
		YamlConfiguration config = load(op);
		String name = w.getName();
		if(name.equals("Mines") || name.contains("SkyBlock")) {name = "SkyBlock";}
		String path = "inventory." + name;
		set(config, path, list, true);
		save(config, file(op));
	}
}