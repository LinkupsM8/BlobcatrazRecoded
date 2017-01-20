package com.ToonBasic.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Blobcatraz;
import com.ToonBasic.blobcatraz.utility.Util;

public abstract class Config
{
	protected static final File folder = Blobcatraz.folder;
	
	public void save(File file, YamlConfiguration config)
	{
		try
		{
			if(!file.exists())
			{
				folder.mkdirs();
				file.createNewFile();
				defaults();
			}
			config.save(file);
		} catch(Exception ex)
		{
			String error = "Failed to save " + file + ":\n" + ex.getMessage();
			Util.print(error);
		}
	}
	
	public YamlConfiguration load(File file)
	{
		try
		{
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(!file.exists()) save(file, config);
			config.load(file);
			defaults();
			return config;
		} catch(Exception ex)
		{
			String error = "Failed to load " + file + ":\n" + ex.getMessage();
			Util.print(error);
			return null;
		}
	}
	
	public void defaults() {}
	public void set(String path, Object value, boolean force) {}
	public void reload() {}
}