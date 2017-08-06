package com.SirBlobman.blobcatraz.config.special;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.ItemUtil;

public class Warp implements Comparable<Warp> {
	private String name;
	private Location warp;
	private ItemStack icon;
	
	public Warp(String name, Location warp) {this(name, warp, ItemUtil.newItem(Material.ENDER_PEARL));}
	public Warp(String name, Location warp, ItemStack icon) {
		this.name = name;
		this.warp = warp;
		this.icon = icon;
	}
	
	public String name() {return name;}
	public Location warp() {return warp;}
	public ItemStack icon() {return icon;}
	
	@Override
	public int compareTo(Warp w) {
		String n1 = name();
		String n2 = w.name();
		return n1.compareToIgnoreCase(n2);
	}
}