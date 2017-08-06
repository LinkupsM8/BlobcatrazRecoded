package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.special.Warp;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

public class ConfigWarps extends Config {
	private static final File WARPS = new File(FOLDER, "warps");
	
	public static YamlConfiguration load(String name) {
		try {
			String c = WordUtil.capitalize(name);
			String f = c + ".warp";
			File file = new File(WARPS, f);
			if(file.exists()) {
				YamlConfiguration config = load(file);
				return config;
			} else {
				String error = "You cannot load a warp that does not exist!";
				Util.print(error);
				return null;
			}
		} catch(Throwable ex) {
			String error = "Failed to load warp '" + name + "':";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save(String name, Location l, ItemStack is) {
		try {
			if(name != null && l != null && is != null) {
				String c = WordUtil.capitalize(name);
				String f = c + ".warp";
				File file = new File(WARPS, f);
				if(!file.exists()) {
					WARPS.mkdirs();
					file.createNewFile();
				}
				
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set("name", c);
				config.set("icon", is);
				config.set("location", l);
				save(config, file);
			}
		} catch(Throwable ex) {
			String error = "Failed to save warp '" + name + "':";
			Util.print(error, ex.getCause());
		}
	}
	
	public static List<String> sWarps() {
		List<String> list = Util.newList();
		if(!WARPS.exists()) return list;
		File[] ff = WARPS.listFiles();
		for(File f : ff) {
			String name = f.getName();
			String end = ".warp";
			if(name.endsWith(end)) {
				String s = FilenameUtils.getBaseName(name);
				YamlConfiguration config = load(s);
				String warp = config.getString("name");
				list.add(warp);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public static List<Warp> warps() {
		List<Warp> list = Util.newList();
		List<String> ss = sWarps();
		for(String s : ss) {
			ItemStack icon = icon(s);
			Location loc = warp(s);
			Warp warp = new Warp(s, loc, icon);
			list.add(warp);
		}
		Collections.sort(list);
		return list;
	}
	
	public static boolean exists(String name) {
		String c = WordUtil.capitalize(name);
		List<String> list = sWarps();
		boolean e = list.contains(c);
		return e;
	}
	
	public static ItemStack icon(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			ItemStack is = config.getItemStack("icon");
			return is;
		} else return ItemUtil.AIR;
	}
	
	public static Location warp(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			Object o = config.get("location");
			if(o instanceof Location) {
				Location l = (Location) o;
				return l;
			} else return null;
		} else return null;
	}
	
	public static void delete(String name) {
		String c = WordUtil.capitalize(name);
		String f = c + ".warp";
		File file = new File(WARPS, f);
		file.delete();
	}
}