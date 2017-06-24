package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.config.special.CustomHologram;
import com.SirBlobman.blobcatraz.utility.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class ConfigHolo extends Config {
	private static final File FILE = new File(FOLDER, "holo items.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	private static List<CustomHologram> old = Util.newList();
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			respawn();
			return config;
		} catch(Exception ex) {
			String error = "Failed to load holograms:";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save() {
		try {
			config.set("holograms", old);
			save(config, FILE);
		} catch(Exception ex) {
			String error = "Failed to save holograms:";
			Util.print(error, ex.getCause());
		}
	}
	
	public static void respawn() {
		List<CustomHologram> list = Util.newList();
		for(CustomHologram ch : old) {
			Hologram o = ch.hologram();
			if(o != null) {o.delete();}
			
			ItemStack is = ch.item();
			Location l = ch.location();
			Hologram h = HologramsAPI.createHologram(Core.INSTANCE, l);
			h.appendItemLine(is);
			
			CustomHologram chn = new CustomHologram(o, is, l);
			list.add(chn);
		}
		old = list;
	}
	
	public static void create(ItemStack is, Location l) {
		load();
		CustomHologram ch = new CustomHologram(null, is, l);
		old.add(ch);
		save();
		respawn();
	}
	
	public static List<CustomHologram> near(Player p, int r) {
		List<CustomHologram> list = Util.newList();
		for(CustomHologram ch : old) {
			Location l1 = ch.location();
			Location l2 = p.getLocation();
			double dist = l1.distanceSquared(l2);
			int r2 = (r ^ 2);
			if(dist <= r2) list.add(ch);
		}
		return list;
	}
	
	public static void remove(CustomHologram... chs) {
		for(CustomHologram ch : chs) old.remove(ch);
		save();
		load();
	}
}