package com.ToonBasic.blobcatraz.command.player.warp;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.PublicHandlers;
import com.google.common.collect.Maps;

public class Warp implements ConfigurationSerializable {
	private String name;
	private Location warp;
	private ItemStack icon;
	
	public Warp(Map<String, Object> data) {
		this(
		    (String) data.get("name"),
		    (Location) data.get("location"),
		    (ItemStack) data.get("icon")
		);
	}
	
	public Warp(String name, Location location, ItemStack icon) {
		try {
			this.name = name;
			this.warp = location;
			this.icon = icon;
		} catch(Exception ex) {
			String error = "Invalid Warp!\n" + ex.getMessage();
			PublicHandlers.print(error);
		}
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newTreeMap();
		map.put("name", name);
		map.put("location", warp);
		map.put("icon", icon);
		return map;
	}
	
	public String name() {return name;}
	public Location warp() {return warp;}
	public ItemStack icon() {return icon;}
}