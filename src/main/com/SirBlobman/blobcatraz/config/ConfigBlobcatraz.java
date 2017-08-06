package com.SirBlobman.blobcatraz.config;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigBlobcatraz extends Config {
	private static final File FILE = new File(FOLDER, "blobcatraz.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			defaults();
			return config;
		} catch(Throwable ex) {
			String error = "Failed to load 'blobcatraz.yml':";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save() {
		try {save(config, FILE);}
		catch(Throwable ex) {
			String error = "Failed to save 'blobcatraz.yml':";
			Util.print(error, ex.getCause());
		}
	}
	
	private static void defaults() {
		String motd = "&5Example MOTD";
		World w = Bukkit.getWorlds().get(0);
		Location hub = new Location(w, 0, 64, 0);
		List<String> rules = Util.newList();
		List<String> votes = Util.newList();
		
		set("motd", motd, false);
		set("hub", hub, false);
		set("rules", rules, false);
		set("vote links", votes, false);
		save();
	}
	
	public static void set(String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(force || n) config.set(path, value);
	}

	public static <T> T get(Class<T> clazz, String path) {
		load();
		Object o = config.get(path);
		if(clazz.isInstance(o)) {
			T t = clazz.cast(o);
			return t;
		} else return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(Class<T> clazz, String path) {
		load();
		List<T> list = Util.newList();
		List<?> ll = config.getList(path);
		try {
			list = (List<T>) ll;
			return list;
		} catch(Throwable ex) {return Util.newList();}
	}
}