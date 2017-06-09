package com.ToonBasic.blobcatraz.config;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.utility.Util;

public class CustomHologram implements ConfigurationSerializable {
	private ItemStack icon;
	private Location loc;
	public CustomHologram(ItemStack icon, Location loc) {
		this.icon = icon;
		this.loc = loc;
	}
	
	public CustomHologram(Map<String, Object> map) {
		this(
			(ItemStack) map.get("icon"),
			(Location) map.get("location")
		);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Util.newMap();
		map.put("icon", icon);
		map.put("location", loc);
		return map;
	}
	
	public ItemStack getItem() {return icon;}
	public Location getLocation() {return loc;}
}