package com.ToonBasic.blobcatraz.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigWorth {
	private static final File FOLDER = Core.folder;
	private static final File FILE = new File(FOLDER, "shop.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			if(!FILE.exists()) save();
			config.load(FILE);
			defaults();
			return config;
		} catch(Exception ex) {
			String error = "Failed to load shop values: " + ex.getCause();
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
			config.save(FILE);
		} catch(Exception ex) {
			String error = "Failed to save worth values: " + ex.getCause();
			Util.print(error);
		}
	}
	
	private static void defaults() {
		for(Material mat : Material.values()) {
			if(mat != Material.AIR) {
				String name = mat.name();
				String path = name + ".0";
				set(path, 0.0D, false);
			}
		}
		save();
	}
	
	private static void set(String path, Object value, boolean force) {
		boolean b = (config.get(path) == null);
		if(b || force) config.set(path, value);
	}
	
	public static double worth(ItemStack is) {
		if(is == null) return 0.0D;
		Material mat = is.getType();
		String name = mat.name();
		short data = is.getDurability();
		int amount = is.getAmount();
		
		YamlConfiguration config = load();
		String path = name + "." + data;
		double worth = config.getDouble(path);
		worth = worth * amount;
		return worth;
	}
	
	public static void setWorth(ItemStack is, double worth) {
		if(is == null) return;
		Material mat = is.getType();
		String name = mat.name();
		short data = is.getDurability();
		String path = name + "." + data;
		set(path, worth, true);
		save();
	}
}