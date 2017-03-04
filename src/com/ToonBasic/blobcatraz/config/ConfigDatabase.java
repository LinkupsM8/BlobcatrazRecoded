package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
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

    public static int tokens(OfflinePlayer op) {
        YamlConfiguration config = load(op);
        int votes = config.getInt("tokens");
        return votes;
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
}