package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Blobcatraz;
import com.ToonBasic.blobcatraz.utility.Util;

public class ConfigDatabase
{
	private static final File folder1 = Blobcatraz.folder;
	private static final File folder2 = new File(folder1, "users");
	
	public YamlConfiguration load(OfflinePlayer op)
	{
		try
		{
			UUID uuid = op.getUniqueId();
			String f = uuid + ".yml";
			File file = new File(folder2, f);
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(!file.exists()) save(op, config);
			config.load(file);
			defaults(op, config);
			return config;
		} catch(Exception ex)
		{
			String error = "Failed to load user data for " + op.getName() + ":\n";
			Util.print(error);
			return null;
		}
	}
	
	public void save(OfflinePlayer op, YamlConfiguration config)
	{
		try
		{
			UUID uuid = op.getUniqueId();
			String f = uuid + ".yml";
			File file = new File(folder2, f);
			if(!file.exists())
			{
				folder2.mkdirs();
				file.createNewFile();
				defaults(op, config);
			}
			config.save(file);
		} catch(Exception ex)
		{
			String error = "Failed to save data file for " + op.getName() + ":\n" + ex.getMessage();
			Util.print(error);
		}
	}
	
	private void defaults(OfflinePlayer op, YamlConfiguration config)
	{
		set(config, "name", op.getName(), false);
		set(config, "prefix", "&eMember &f", false);
		set(config, "banned.status", false, false);
		set(config, "votes", 0, false);
		save(op, config);
	}
	
	private void set(YamlConfiguration config, String path, Object value, boolean force)
	{
		boolean b = (config.get(path) == null);
		if(b || force) config.set(path, value);
	}
}