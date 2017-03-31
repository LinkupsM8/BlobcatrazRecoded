package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigWarps {
	private static final File FOLDER = new File(Core.folder, "warps");
	public ConfigWarps() {}
	
	public static YamlConfiguration load(String name) {
		try {
			name = StringUtils.capitalize(name);
			String fname = name + ".warp";
			File file = new File(FOLDER, fname);
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(file.exists()) {
				config.load(file);
				return config;
			} else {
				String error = "You cannot load a warp that does not exist";
				Util.print(error);
				return null;
			}
		} catch(Exception ex) {
			String error = "Failed to load warp " + name + ":\n" + ex.getCause();
			Util.print(error);
			return  null;
		}
	}
	
	public static void save(String name, Location l, ItemStack icon) {
		try {
			if(name != null && l != null && icon != null) {
				name = StringUtils.capitalize(name);
				if(!FOLDER.exists()) FOLDER.mkdirs();
				World w = l.getWorld();
				String world = w.getName();
				double x = l.getX();
				double y = l.getY();
				double z = l.getZ();
				double yaw = l.getYaw();
				double pitch = l.getPitch();
				String fname = name + ".warp";
				File file = new File(FOLDER, fname);
				if(!file.exists()) file.createNewFile();
				
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set("name", name);
				config.set("icon", icon);
				config.set("world", world);
				config.set("x", x);
				config.set("y", y);
				config.set("z", z);
				config.set("yaw", yaw);
				config.set("pitch", pitch);
				config.save(file);
			}
		} catch(Exception ex) {
			String error = "Failed to save warp " + name + ":\n" + ex.getCause();
			Util.print(error);
			return;
		}
	}
	
	public static boolean exists(String name) {
		List<String> list = stringWarps();
		boolean exist = list.contains(name);
		return exist;
	}
	
	public static ItemStack getIcon(String warp) {
		if(exists(warp)) {
			YamlConfiguration config = load(warp);
			ItemStack icon = config.getItemStack("icon");
			return icon;
		} else return null;
	}
	
	public static Location getWarp(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			String w = config.getString("world");
			World world = Bukkit.getWorld(w);
			double x = config.getDouble("x");
			double y = config.getDouble("y");
			double z = config.getDouble("z");
			float pitch = (float) config.getDouble("pitch");
			float yaw = (float) config.getDouble("yaw");
			Location warp = new Location(world, x, y, z, yaw, pitch);
			return warp;
		} else return null;
	}
	
	public static List<String> stringWarps() {
		if(!FOLDER.exists()) FOLDER.mkdirs();
		File[] files = FOLDER.listFiles();
		List<String> list = Util.newList();
		for(File file : files) {
			String name = file.getName();
			String end = ".warp";
			if(name.endsWith(end)) {
				String nname = FilenameUtils.getBaseName(name);
				YamlConfiguration config = load(nname);
				String warp = config.getString("name");
				list.add(warp);
			}
		}
		Collections.sort(list);
		return list;
	} 
	
	public static List<Warp> warps() {
		List<Warp> list = Util.newList();
		for(String name : stringWarps()) {
			ItemStack icon = getIcon(name);
			Location loc = getWarp(name);
			Warp warp = new Warp(name, icon, loc);
			list.add(warp);
		}
		list.sort(new WarpComparator());
		return list;
	}
	
	public static void delete(String name) {
		name = StringUtils.capitalize(name);
		String file = name + ".warp";
		File warp = new File(FOLDER, file);
		warp.delete();
	}
}