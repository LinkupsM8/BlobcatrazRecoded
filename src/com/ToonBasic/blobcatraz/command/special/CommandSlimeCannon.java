package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.util.Vector;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandSlimeCannon extends ICommand {
	public CommandSlimeCannon() {super("slimecannon", "", "blobcatraz.special.slimecannon");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Location l = p.getEyeLocation();
		Vector eye = l.getDirection();
		Vector v = eye.multiply(2);
		World w = p.getWorld();
		Slime s = w.spawn(l, Slime.class);
		s.setSize(1);
		s.setVelocity(v);
		Bukkit.getScheduler().runTaskLater(Core.instance, new Explode(s), 20L);
	}
	
	public class Explode implements Runnable {
		private Slime slime;
		public Explode(Slime slime) {this.slime = slime;}
		
		@Override
		public void run() {
			Location l = slime.getLocation();
			World w = l.getWorld();
			w.createExplosion(l, 0.0F);
			slime.remove();
		}
	}
}