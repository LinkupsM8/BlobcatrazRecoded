package com.SirBlobman.blobcatraz.config.special;

import com.SirBlobman.blobcatraz.utility.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomHologram implements ConfigurationSerializable {
	private Hologram holo;
	private ItemStack icon;
	private Location loc;
	public CustomHologram(Hologram holo, ItemStack icon, Location loc) {
		this.holo = holo;
		this.icon = icon;
		this.loc = loc;
	}
	
	public CustomHologram(Map<String, Object> map) {
		this(
			null,
			(ItemStack) map.get("icon"),
			(Location) 	map.get("location")
		);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Util.newMap();
		map.put("icon", icon);
		map.put("location", loc);
		return map;
	}
	
	public Hologram hologram() {return holo;}
	public ItemStack item() {return icon;}
	public Location location() {return loc;}
}