package com.ToonBasic.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

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
		
	}
	
	private void defaults() {
		
	}
	
	private void set(String path, Object value, boolean force) {
		
	}
}