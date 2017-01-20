package com.ToonBasic.blobcatraz;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class Blobcatraz extends JavaPlugin 
{
	public static Blobcatraz instance;
	public static File folder;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		folder = getDataFolder();
		configs();
		commands();
		events();
	}
	
	@Override
	public void onDisable() {}
	
	private void configs()
	{
		
	}
	
	private void commands()
	{
		
	}
	
	private void events()
	{
		
	}
}