package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigCustomItems extends Config {
	private static final File FILE = new File(FOLDER, "custom items.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			defaults();
			return config;
		} 
		catch(Exception ex) {
			String error = "Failed to load custom items:\n" + ex.getCause();
			Util.print(error);
			return null;
		}
	}
	
	public static void save() {
		try {
			if(!FILE.exists()) {
				FOLDER.mkdirs();
				FILE.createNewFile();
			}
			save(config, FILE);
		}
		catch(Exception ex) {
			String error = "Failed to save custom items:\n" + ex.getCause();
			Util.print(error);
		}
	}
	
	private static void defaults() {
		ItemStack notch_apple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
		set("items.notch_apple", notch_apple, false);
		save();
	}
	
	private static void set(String path, Object value, boolean force) {
		boolean b = (config.get(path) == null);
		if(b || force) config.set(path, value);
	}
	
	public static void create(String id, ItemStack is) {
		load();
		id = id.toLowerCase().replace(' ', '_');
		String path = "items." + id;
		set(path, is, true);
		save();
	}
	
	public static void delete(String id) {
		load();
		id = id.toLowerCase().replace(' ', '_');
		String path = "items." + id;
		set(path, null, true);
		save();
	}
	
	public static Map<String, ItemStack> items() {
		load();
		Map<String, ItemStack> map = Util.newMap();
		ConfigurationSection set = config.getConfigurationSection("items");
		if(set == null) {
			save();
			return map;
		}
		Map<String, Object> vals = set.getValues(false);
		for(Entry<String, Object> e : vals.entrySet()) {
			String id = e.getKey();
			Object o = e.getValue();
			if(o instanceof ItemStack) {
				ItemStack is = (ItemStack) o;
				map.put(id, is);
			}
		}
		return map;
	}
}