package com.SirBlobman.blobcatraz.flatgenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	@Override
	public void onEnable() {
		System.out.println("Enabled VoidGenerator addon!");
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String id) {
		FlatGenerator fg = new FlatGenerator(id);
		return fg;
	}
}