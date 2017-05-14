package com.ToonBasic.blobcatraz.command.special;

import static com.ToonBasic.blobcatraz.command.staff.CommandMobSpawn.INVALID;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandKillAll extends ICommand {
	public CommandKillAll() {super("killall", "<type> [radius]", "blobcatraz.special.killall", "bkill", "bbutcher");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String type = args[0].toUpperCase();
		EntityType et = null;
		try {et = EntityType.valueOf(type);}
		catch(Exception ex) {
			p.sendMessage("Invalid Entity!"); 
			return;
		}
		
		if(INVALID.contains(et)) {
			p.sendMessage("Invalid Entity!");
			return;
		}
		int radius = -1;
		try{radius = Integer.parseInt(args[1]);} catch(Exception ex) {}
		int count = killall(p, et, radius);
		String msg = prefix + "Killed " + count + " entities in a radius of " + radius;
		p.sendMessage(msg);
	}
	
	private int killall(Player p, EntityType type, int r) {
		World w = p.getWorld();
		List<Entity> ents = Util.newList();
		if(r == -1) {ents = w.getEntities();} 
		else {ents = p.getNearbyEntities(r, r, r);}
		
		int count = 0;
		for(Entity e : ents) {
			EntityType et = e.getType();
			if(et == type) {
				Location l = e.getLocation();
				w.strikeLightningEffect(l);
				if(e instanceof LivingEntity) {
					LivingEntity le = (LivingEntity) e;
					le.setHealth(0.0D);
				} else e.remove();
				count++;
			}
		}
		return count;
	}
}
