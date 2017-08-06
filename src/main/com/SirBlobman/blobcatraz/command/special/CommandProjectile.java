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
		World w = p.getWorld();
		Location e = p.getEyeLocation();
		Vector d = e.getDirection();
		Vector m = d.multiply(2);
		
		String sub = args[0].toLowerCase();
		if(sub.equals("skull")) {
			WitherSkull ws = w.spawn(e, WitherSkull.class);
			ws.setVelocity(m);
			ws.setYield(10.0F);
		} else if(sub.equals("fireball")) {
			Fireball f = w.spawn(e, Fireball.class);
			f.setVelocity(m);
			f.setYield(10.0F);
		} else if(sub.equals("egg")) {
			Egg egg = w.spawn(e, Egg.class);
			egg.setVelocity(m);
			egg.setGlowing(true);
		} else if(sub.equals("snowball")) {
			Snowball sb = w.spawn(e, Snowball.class);
			sb.setVelocity(m);
			sb.setGlowing(true);
		} else if(sub.equals("arrow")) {
			Arrow ar = w.spawn(e, Arrow.class);
			ar.setVelocity(m);
			Spigot sp = ar.spigot();
			sp.setDamage(Double.MAX_VALUE);
		} else {
			String error = prefix + "Invalid projectile!";
			p.sendMessage(error);
		}
	}
}