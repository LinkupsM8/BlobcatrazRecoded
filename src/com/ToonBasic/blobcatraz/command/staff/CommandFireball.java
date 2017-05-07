package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandFireball extends ICommand {
	public CommandFireball() {super("fireball", "[skull/snowball/egg]", "blobcatraz.staff.fireball", "comet", "firecharge");}
	
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
		if(args.length > 1) {
			String sub = args[0].toLowerCase();
			if(sub.equals("skull")) {
				WitherSkull sk = w.spawn(shoot, WitherSkull.class);
				sk.setYield(10.0F);
			} else if(sub.equals("snowball")) {
				Snowball snow = w.spawn(shoot, Snowball.class);
				snow.setGlowing(true);
			} else if(sub.equals("fireball")) {
				Fireball fire = w.spawn(shoot, Fireball.class);
				fire.setYield(10.0F);
			} else if(sub.equals("egg")) {
				Egg egg = w.spawn(shoot, Egg.class);
				egg.setGlowing(true);
			} else {
				Fireball fire = w.spawn(shoot, Fireball.class);
				fire.setYield(10.0F);
			}
		} else {
			Fireball fire = w.spawn(shoot, Fireball.class);
			fire.setYield(10.0F);
		}
	}
}