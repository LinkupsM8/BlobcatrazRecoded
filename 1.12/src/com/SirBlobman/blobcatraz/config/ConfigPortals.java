package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigPortals extends Config {
	private static final File PORTALS = new File(FOLDER, "portals");
	
	public static void save(String name, Location l, Location l1, Location l2) {
		try {
			if((name != null) && (l != null) && (l1 != null) && (l2 != null)) {
				String f = name + ".portal";
				File file = new File(PORTALS, f);
				if(!file.exists()) {
					PORTALS.mkdirs();
					file.createNewFile();
				}
				
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set("name", name);
				config.set("destination", l);
				config.set("primary", l1);
				config.set("secondary", l2);
				save(config, file);
			}
		} catch(Throwable ex) {
			String error = "Failed to save portal '" + name + "':";
			Util.print(error, ex.getCause());
		}
	}
	
	public static YamlConfiguration load(String name) {
		try {
			String f = name + ".portal";
			File file = new File(PORTALS, f);
			if(file.exists()) {
				YamlConfiguration config = load(file);
				return config;
			} else {
				String error = "You cannot load a portal that does not exist!";
				Util.print(error);
				return null;
			}
		} catch(Exception ex) {
			String error = "Failed to load portal '" + name + "':";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static List<String> portalList() {
		List<String> list = Util.newList();
		if(!PORTALS.exists()) return list;
		File[] ff = PORTALS.listFiles();
		for(File file : ff) {
			String f = file.getName();
			String end = ".portal";
			if(f.endsWith(end)) {
				String name = FilenameUtils.getBaseName(f);
				list.add(name);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public static boolean exists(String name) {
		List<String> list = portalList();
		boolean e = list.contains(name);
		return e;
	}
	
	public static Location destination(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			Object o = config.get("destination");
			if(o instanceof Location) {
				Location l = (Location) o;
				return l;
			} else return null;
		} else return null;
	}
	
	public static Location primary(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			Object o = config.get("primary");
			if(o instanceof Location) {
				Location l = (Location) o;
				return l;
			} else return null;
		} else return null;
	}
	
	public static Location secondary(String name) {
		if(exists(name)) {
			YamlConfiguration config = load(name);
			Object o = config.get("secondary");
			if(o instanceof Location) {
				Location l = (Location) o;
				return l;
			} else return null;
		} else return null;
	}
	
	public static void delete(String name) {
		String f = name + ".portal";
		File file = new File(PORTALS, f);
		file.delete();
	}
}