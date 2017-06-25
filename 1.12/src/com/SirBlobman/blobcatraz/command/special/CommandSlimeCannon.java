package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.util.Vector;

public class CommandSlimeCannon extends PlayerCommand {
	public CommandSlimeCannon() {super("slimecannon", "", "blobcatraz.special.slimecannon");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getEyeLocation();
		Vector d = l.getDirection();
		Vector m = d.multiply(2);
		World w = l.getWorld();
		Slime s = w.spawn(l, Slime.class);
		if(s != null) {
			p.setCollidable(false);
			s.setCollidable(false);
			s.setInvulnerable(true);
			s.setSize(5);
			s.setVelocity(m);
			Bukkit.getScheduler().runTaskLater(Core.INSTANCE, new Explode(p, s), 20L);
		}
	}
	
	private class Explode implements Runnable {
		private Player player;
		private Slime slime;
		public Explode(Player p, Slime s) {this.player = p; this.slime = s;}
		
		@Override
		public void run() {
			Location l = slime.getLocation();
			World w = l.getWorld();
			w.createExplosion(l, 0.0F);
			slime.remove();
			player.setCollidable(true);
		}
	}
}