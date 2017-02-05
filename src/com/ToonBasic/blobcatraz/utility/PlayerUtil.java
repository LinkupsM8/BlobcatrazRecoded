package com.ToonBasic.blobcatraz.utility;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerUtil extends Util {
	public static void ping(Player p) {
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 100.0F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
}