package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.Spigot;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

public class CommandProjectile extends PlayerCommand {
	public CommandProjectile() {super("projectile", "<skull/fireball/snowball/egg/arrow>", "blobcatraz.special.projectile", "proj", "shoot");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		World w = l.getWorld();
		float ya = l.getYaw();
		float pi = l.getPitch();
		
		Location e = p.getEyeLocation();
		Vector v = e.toVector();
		Vector d = e.getDirection();
		Vector m = d.multiply(2);
		Vector a = v.add(m);
		Location s = a.toLocation(w, ya, pi);
		
		String sub = args[0].toLowerCase();
		if(sub.equals("skull")) {
			WitherSkull ws = w.spawn(s, WitherSkull.class);
			ws.setYield(10.0F);
		} else if(sub.equals("fireball")) {
			Fireball f = w.spawn(s, Fireball.class);
			f.setYield(10.0F);
		} else if(sub.equals("egg")) {
			Egg egg = w.spawn(s, Egg.class);
			egg.setGlowing(true);
		} else if(sub.equals("snowball")) {
			Snowball sb = w.spawn(s, Snowball.class);
			sb.setGlowing(true);
		} else if(sub.equals("arrow")) {
			Arrow ar = w.spawn(s, Arrow.class);
			Spigot sp = ar.spigot();
			sp.setDamage(Double.MAX_VALUE);
		} else {
			String error = prefix + "Invalid projectile!";
			p.sendMessage(error);
		}
	}
}