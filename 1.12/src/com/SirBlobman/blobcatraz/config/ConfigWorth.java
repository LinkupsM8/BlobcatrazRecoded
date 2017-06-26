package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigWorth extends Config {
	private static final File FILE = new File(FOLDER, "shop.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			defaults();
			return config;
		} catch(Exception ex) {
			String error = "Failed to load shop values: ";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save() {
		try {save(config, FILE);}
		catch(Exception ex) {
			String error = "Failed to save shop values: ";
			Util.print(error, ex.getCause());
		}
	}
	
	private static void defaults() {
		Material[] mats = Material.values();
		for(Material mat : mats) {
			if(mat != Material.AIR) {
				String name = mat.name();
				String path = name + ".0";
				set(path, 1.0D, false);
			}
		}
		save();
	}
	
	private static void set(String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(n || force) config.set(path, value);
	}
	
	public static double worth(ItemStack is) {
		if(ItemUtil.air(is)) return 0.0D;
		Material mat = is.getType();
		String name = mat.name();
		short data = is.getDurability();
		int amount = is.getAmount();
		
		load();
		String path = name + "." + data;
		double worth = 0.0D;
		
		String disp = ItemUtil.name(is);
		if(disp.equals(Util.color("&4&ki&1Overpowered&4&ki&r"))) worth = 150000000000.00D;
		else worth = config.getDouble(path);
		double n = worth * amount;
		return n;
	}
	
	public static void setWorth(ItemStack is, double worth) {
		if(ItemUtil.air(is)) return;
		Material mat = is.getType();
		String name = mat.name();
		short data = is.getDurability();
		String path = name + "." + data;
		set(path, worth, true);
		save();
	}
}