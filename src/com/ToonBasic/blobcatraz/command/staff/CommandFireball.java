package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandFireball extends ICommand {
	public CommandFireball() {super("fireball", "[skull]", "blobcatraz.staff.fireball", "comet", "firecharge");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Location l = p.getLocation();
		World w = p.getWorld();
		
		Location eye = p.getEyeLocation();
		Vector veye = eye.toVector();
		Vector dir = l.getDirection().multiply(2);
		Vector add = veye.add(dir);
		Location shoot = add.toLocation(w, l.getYaw(), l.getPitch());
		Fireball fire = w.spawn(shoot, Fireball.class);
		fire.setYield(10.0F);
	}
}