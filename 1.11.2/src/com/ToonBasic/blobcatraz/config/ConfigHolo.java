package com.ToonBasic.blobcatraz.config;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class ConfigHolo {
	private static final File FOLDER = Core.folder;
	private static final File FILE = new File(FOLDER, "holographic items.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	private static List<CustomHologram> list = Util.newList();
	
	@SuppressWarnings("unchecked")
	public static YamlConfiguration load() {
		try {
			if(!FILE.exists()) save();
			config.load(FILE);
			list = (List<CustomHologram>) config.getList("holograms");
			respawn();
			return config;
		} catch(Exception ex) {
			String error = "Failed to load holograms: " + ex.getCause();
			Util.print(error);
			return null;
		}
	}
	
	public static void save() {
		try {
			if(!FILE.exists()) {
				FOLDER.mkdirs();
				FILE.createNewFile();
			}
			config.set("holograms", list);
			config.save(FILE);
		} catch(Exception ex) {
			String error = "Failed to save holograms: " + ex.getCause();
			Util.print(error);
		}
	}
	
	public static void create(ItemStack is, Location l) {
		load();
		CustomHologram ch = new CustomHologram(is, l);
		list.add(ch);
		save();
		respawn();
	}
	
	public static void respawn() {
		Collection<Hologram> old = HologramsAPI.getHolograms(Core.instance);
		for(Hologram h : old) {h.delete();}
		
		for(CustomHologram ch : list) {
			ItemStack is = ch.getItem();
			Location l = ch.getLocation();
			Hologram h = HologramsAPI.createHologram(Core.instance, l);
			h.appendItemLine(is);
		}
	}
	
	public static List<CustomHologram> near(Player p, int r) {
		List<CustomHologram> nlist = Util.newList();
		for(CustomHologram ch : list) {
			Location hl = ch.getLocation();
			Location pl = p.getLocation();
			int px = pl.getBlockX();
			int hx = hl.getBlockX();
			int py = pl.getBlockY() + 1;
			int hy = hl.getBlockY();
			int pz = pl.getBlockZ();
			int hz = hl.getBlockZ();
			
			int dx = Math.abs(px - hx);
			int dy = Math.abs(py - hy);
			int dz = Math.abs(pz - hz);
			
			if((dx <= r) && (dy <= r) && (dz <= r)) nlist.add(ch);
		}
		return nlist;
	}
	
	public static void remove(CustomHologram... chs) {
		for(CustomHologram ch : chs) list.remove(ch);
		save();
		load();
	}
}