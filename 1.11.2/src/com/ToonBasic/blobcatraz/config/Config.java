package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Core;

public class Config {
	protected static final File FOLDER = Core.folder;
	
	public static YamlConfiguration load(File file) throws IOException, InvalidConfigurationException {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) save(config, file);
		config.load(file);
		return config;
	}
	
	public static void save(YamlConfiguration config, File file) throws IOException, IllegalArgumentException {
		if(!file.exists()) {
			FOLDER.mkdirs();
			file.createNewFile();
		}
		config.save(file);
	}
}