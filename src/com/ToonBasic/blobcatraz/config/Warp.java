package com.ToonBasic.blobcatraz.config;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Warp {
	private String name;
	private Location warp;
	private ItemStack icon;
	
	public Warp(String name, ItemStack icon, Location warp) {
		this.name = name;
		this.icon = icon;
		this.warp = warp;
	}
	
	public String name() {return name;}
	public Location warp() {return warp;}
	public ItemStack icon() {return icon;}
}