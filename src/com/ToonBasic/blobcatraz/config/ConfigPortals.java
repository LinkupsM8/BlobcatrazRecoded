package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigPortals {
	
	private static final File DIR = new File(Core.folder, "portals");
	
	public static void save(String name, Location dest, Location primary, Location secondary) {
		
		try {
			
			if(name != null && dest != null && primary != null && secondary != null) {
				
				if (!DIR.exists()) DIR.mkdirs();
				File file = new File(DIR, name + ".portal");
				if(!file.exists()) file.createNewFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set("name", name);
				config.set("dest.world", dest.getWorld().getName());
				config.set("dest.x", dest.getX());
				config.set("dest.y", dest.getY());
				config.set("dest.z", dest.getZ());
				config.set("dest.yaw", dest.getYaw());
				config.set("dest.pitch", dest.getPitch());
				config.set("primary.world", primary.getWorld().getName());
				config.set("primary.x", primary.getX());
				config.set("primary.y", primary.getY());
				config.set("primary.z", primary.getZ());
				config.set("secondary.x", secondary.getX());
				config.set("secondary.y", secondary.getY());
				config.set("secondary.z", secondary.getZ());
				config.save(file);
			}
			
		} catch(Exception e) {
			
			Util.print("Failed to save portal " + name + ":\n" + e.getCause());
			return;
		}
	}
	
	public static YamlConfiguration load(String name) {
		
		try {
			
			File file = new File(DIR, name + ".portal");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			
			if(file.exists()) {
				cfg.load(file);
				return cfg;
			} else {
				Util.print("You cannot load a portal that does not exist!");
				return null;
			}
			
		} catch(Exception e) {
			
			Util.print("Failed to load portal " + name + ":\n" + e.getCause());
			return null;
		}
	}
	
	public static Location getDestination(String name) {
		
		if(exists(name)) {
			
			YamlConfiguration config = load(name);
			return new Location(Bukkit.getWorld(config.getString("dest.world")), config.getDouble("dest.x"), config.getDouble("dest.y"), config.getDouble("dest.z"), (float) config.getDouble("dest.yaw"), (float) config.getDouble("dest.pitch"));
			
		} else return null;
		
	}
	
	public static Location getPrimary(String name) {
		
		if(exists(name)) {
			
			YamlConfiguration config = load(name);
			return new Location(Bukkit.getWorld(config.getString("primary.world")), config.getDouble("primary.x"), config.getDouble("primary.y"), config.getDouble("primary.z"));
			
		} else return null;
		
	}
	
	public static Location getSecondary(String name) {
		
		if(exists(name)) {
			
			YamlConfiguration config = load(name);
			return new Location(Bukkit.getWorld(config.getString("primary.world")), config.getDouble("secondary.x"), config.getDouble("secondary.y"), config.getDouble("secondary.z"));
			
		} else return null;
		
	}
	
	public static boolean exists(String name) {
		return getPortalList().contains(name);
	}
	
	public static List<String> getPortalList() {
		
		if(!DIR.exists()) DIR.mkdirs();
		File[] files = DIR.listFiles();
		List<String> list = Util.newList();
		for(File file : files) {
			list.add(FilenameUtils.getBaseName(file.getName()));
		}
		Collections.sort(list);
		return list;
	}
	
	public static void delete(String name) {
		
		File portal = new File(DIR, name + ".portal");
		portal.delete();
	}
}