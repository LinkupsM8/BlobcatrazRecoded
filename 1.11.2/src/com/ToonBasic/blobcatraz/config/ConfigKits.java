package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigKits {
	private static final File FOLDER = new File(Core.folder, "kits");
	
	public static YamlConfiguration load(String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		try {
			String f = name + ".kit";
			File file = new File(FOLDER, f);
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(!file.exists()) save(name, config);
			config.load(file);
			return config;
		} catch(Exception ex) {
			String error = "Failed to load kit " + name + ":\n" + ex.getCause();
			Util.print(error);
			return null;
		}
	}
	
	public static void save(String name, YamlConfiguration config) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		try {
			if(!FOLDER.exists()) FOLDER.mkdirs();
			String f = name + ".kit";
			File file = new File(FOLDER, f);
			if(!file.exists()) file.createNewFile();
			config.save(file);
		} catch(Exception ex) {
			String error = "Failed to save " + name + ".kit:\n" + ex.getCause();
			Util.print(error);
		}
	}
	
	public static List<ItemStack> kit(String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		YamlConfiguration config = load(name);
		List<ItemStack> list = Util.newList();
		try {
			ConfigurationSection cs = config.getConfigurationSection("items");
			Map<String, Object> mso = cs.getValues(false);
			Set<String> ss = mso.keySet();
			for(String s : ss) {
				int i = Integer.parseInt(s);
				ItemStack is = config.getItemStack("items." + i);
				list.add(is);
			}
		} catch(Exception ex) {
			String error = "There was an error getting a kit with the name " + name + ":\n" + ex.getCause();
			Util.print(error);
		}
		return list;
	}
	
	public static void give(Player p, String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		if(exists(name)) {
			PlayerInventory pi = p.getInventory();
			List<ItemStack> kit = kit(name);
			List<ItemStack> toDrop = Util.newList();
			for(ItemStack is : kit) {
				Map<Integer, ItemStack> left = pi.addItem(is);
				if(!left.isEmpty()) {
					for(ItemStack is2 : left.values()) toDrop.add(is2);
				}
			}
			if(!toDrop.isEmpty()) {
				p.sendMessage("Your inventory was full, the rest of your kit is on the floor!");
				for(ItemStack is3 : toDrop) {
					World w = p.getWorld();
					Item i = w.dropItemNaturally(p.getLocation(), is3);
					i.setGravity(false);
				}
			}
		}
	}
	
	public static boolean create(Inventory i, String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		YamlConfiguration config = load(name);
		config.set("name", name);
		int o = 0;
		ItemStack[] items = i.getStorageContents();
		for(ItemStack is : items) {
			if(is != null && is.getType() != Material.AIR) {
				config.set("items." + o, is);
				o++;
			}
		}
		save(name, config);
		return true;
	}
	
	public static List<String> kits() {
		if(!FOLDER.exists()) FOLDER.mkdirs();
		List<String> list = Util.newList();
		File[] files = FOLDER.listFiles();
		for(File file : files) {
			String name = file.getName();
			String end = ".kit";
			if(name.endsWith(end)) {
				String kit = FilenameUtils.getBaseName(name);
				YamlConfiguration config = load(kit);
				String name2 = config.getString("name");
				name2 = name2.toLowerCase();
				name2 = StringUtils.capitalize(name2);
				list.add(name2);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public static void delete(String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		String f = name + ".kit";
		File file = new File(FOLDER, f);
		file.delete();
	}
	
	public static boolean exists(String name) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		List<String> kits = kits();
		boolean exists = kits.contains(name);
		return exists;
	}
	
	public static void kitToChest(String name, Chest c) {
		name = name.toLowerCase();
		name = StringUtils.capitalize(name);
		if(exists(name)) {
			Inventory i = c.getInventory();
			List<ItemStack> items = kit(name);
			i.clear();
			
			for(ItemStack is : items) {
				Map<Integer, ItemStack> left = i.addItem(is);
				if(!left.isEmpty()) break;
			}
		}
	}
}