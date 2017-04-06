package com.ToonBasic.blobcatraz.utility;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerUtil extends Util {
	public static void ping(Player p) {
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 0.1F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
	
	public static void sonic(Player p) {
		Location l = p.getLocation();
		String s = "tool.sonic.screwdriver";
		float v = 0.1F;
		float pi = 0.1F;
		p.playSound(l, s, v, pi);
	}
	
	public static Block lookBlock(Player p) {
		Set<Material> set = null;
		Block b = p.getTargetBlock(set, 200);
		return b;
	}
	
	public static Location lookLocation(Player p) {
		Location l = p.getEyeLocation();
		Vector v = l.getDirection().normalize();
		for(int i = 1; i <= 200; i++) {
			l.add(v);
			Block b = l.getBlock();
			Material mat = b.getType();
			if(mat != Material.AIR) {return l;}
		}
		return lookBlock(p).getLocation();
	}
	
	public static String possesive(String name) {
		boolean s = name.toLowerCase().endsWith("s");
		String poss = s ? name + "'" : name + "'s";
		return poss;
	}
}