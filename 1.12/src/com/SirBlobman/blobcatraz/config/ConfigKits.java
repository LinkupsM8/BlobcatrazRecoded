package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

public class ConfigKits extends Config {
	private static final File KITS = new File(FOLDER, "kits");
	
	public static YamlConfiguration load(String name) {
		try {
			String c = WordUtil.capitalize(name);
			String f = c + ".kit";
			File file = new File(KITS, f);
			if(file.exists()) {
				YamlConfiguration config = load(file);
				return config;
			} else {
				String error = "You cannot load a kit that does not exist!";
				Util.print(error);
				return null;
			}
		} catch(Throwable ex) {
			String error = "Failed to load kit '" + name + "':";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save(String name, List<ItemStack> items) {
		try {
			String c = WordUtil.capitalize(name);
			String f = c + ".kit";
			File file = new File(KITS, f);
			if(!file.exists()) {
				KITS.mkdirs();
				file.createNewFile();
			}
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("name", c);
			config.set("items", items);
			save(config, file);
		} catch(Throwable ex) {
			String error = "Failed to save kit '" + name + "':";
			Util.print(error, ex.getCause());
		}
	}
	
	public static List<String> kits() {
		List<String> list = Util.newList();
		if(!KITS.exists()) return list;
		File[] ff = KITS.listFiles();
		for(File file : ff) {
			String f = file.getName();
			String end = ".kit";
			if(f.endsWith(end)) {
				String name = FilenameUtils.getBaseName(f);
				YamlConfiguration config = load(name);
				String name2 = config.getString("name");
				if(name2 != null) {
					String name3 = WordUtil.capitalize(name2);
					list.add(name3);
				}
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public static boolean exists(String name) {
		String c = WordUtil.capitalize(name);
		List<String> list = kits();
		boolean e = list.contains(c);
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ItemStack> kit(String name) {
		String c = WordUtil.capitalize(name);
		YamlConfiguration config = load(c);
		List<ItemStack> list = Util.newList();
		try {
			List<?> items = config.getList("items");
			list = (List<ItemStack>) items;
			return list;
		} catch(Throwable ex) {
			String error = "There was an error loading the kit with the name '" + name + "':";
			Util.print(error, ex.getCause());
			return Util.newList();
		}
	}
	
	public static void create(String name, Inventory i) {
		String c = WordUtil.capitalize(name);
		ItemStack[] items = i.getContents();
		List<ItemStack> list = Util.newList(items);
		save(c, list);
	}
	
	public static void kitToChest(String name, Chest ch) {
		String c = WordUtil.capitalize(name);
		if(exists(c)) {
			Inventory i = ch.getInventory();
			i.clear();
			List<ItemStack> list = kit(c);
			for(ItemStack is : list) {
				Map<Integer, ItemStack> map = i.addItem(is);
				if(!map.isEmpty()) break;
			}
		}
	}
	
	public static void delete(String name) {
		String c = WordUtil.capitalize(name);
		String f = c + ".kit";
		File file = new File(KITS, f);
		file.delete();
	}
}