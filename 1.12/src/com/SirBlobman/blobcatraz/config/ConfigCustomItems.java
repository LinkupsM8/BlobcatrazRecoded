package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigCustomItems extends Config {
	private static final File FILE = new File(FOLDER, "custom items.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			defaults();
			return config;
		} catch(Throwable ex) {
			String error = "Failed to load custom items:\n" + ex.getCause();
			Util.print(error);
			return null;
		}
	}
	
	public static void save() {
		try {save(config, FILE);}
		catch(Throwable ex) {
			String error = "Failed to save custom items:\n" + ex.getCause();
			Util.print(error);
		}
	}
	
	private static void defaults() {
		ItemStack notch = ItemUtil.newItem(Material.GOLDEN_APPLE, 1, 1);
		set("items.notch_apple", notch, false);
		save();
	}
	
	private static void set(String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(n || force) config.set(path, value);
	}
	
	public static void create(String id, ItemStack is) {
		load();
		String l = id.toLowerCase();
		String r = l.replace(' ', '_');
		String p = "items." + r;
		set(p, is, true);
		save();
	}
	
	public static void delete(String id) {
		load();
		String l = id.toLowerCase();
		String r = l.replace(' ', '_');
		String p = "items." + r;
		set(p, null, true);
		save();
	}
	
	public static Map<String, ItemStack> items() {
		load();
		Map<String, ItemStack> map = Util.newMap();
		ConfigurationSection cs = config.getConfigurationSection("items");
		if(cs == null) {
			save();
			return map;
		} else {
			Map<String, Object> vals = cs.getValues(false);
			Set<Entry<String, Object>> set = vals.entrySet();
			for(Entry<String, Object> e : set) {
				Object o = e.getValue();
				if(o instanceof ItemStack) {
					String id = e.getKey();
					ItemStack is = (ItemStack) o;
					map.put(id, is);
				}
			}
			return map;
		}
	}
}