package com.ToonBasic.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ToonBasic.blobcatraz.Blobcatraz;
import com.ToonBasic.blobcatraz.utility.Util;

public abstract class Config
{
	protected static final File folder = Blobcatraz.folder;
	
	public static void save(File file, YamlConfiguration config)
	{
		if(!file.exists())
		{
			try
			{
				folder.mkdirs();
				file.createNewFile();
			} catch(Exception ex)
			{
				String error = "Failed to save " + file + ":\n" + ex.getMessage();
				Util.print(error);
			}
		}
	}
}