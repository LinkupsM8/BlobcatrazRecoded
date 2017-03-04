package com.ToonBasic.blobcatraz.utility;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerUtil extends Util {
	public static void ping(Player p) {
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 100.0F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
	
	public static void sonic(Player p) {
		Location l = p.getLocation();
		String s = "tool.sonic.screwdriver";
		float v = 100.0F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
	
	public static Block lookBlock(Player p) {
		Set<Material> set = null;
		Block b = p.getTargetBlock(set, 200);
		return b;
	}
	
	public static Location lookLocation(Player p) {
		Block b = lookBlock(p);
		Location l = b.getLocation();
		return l;
	}
}