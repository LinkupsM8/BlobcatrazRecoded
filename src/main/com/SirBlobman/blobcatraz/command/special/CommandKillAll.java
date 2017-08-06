package com.SirBlobman.blobcatraz.command.special;

import static com.SirBlobman.blobcatraz.command.staff.CommandMobSpawn.INVALID;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandKillAll extends PlayerCommand {
	public CommandKillAll() {super("killall", "<type> [radius]", "blobcatraz.special.killall", "bkill", "bbutcher");}
	
	@Override
	public void run(Player p, String[] args) {
		String type = args[0].toUpperCase();
		int r = -1;
		if(args.length > 1) r = NumberUtil.getInteger(args[1]);
		if(type.equals("ALL")) {
			int count = killall(p, r);
			String msg = Util.format(prefix + "Killed %1s entities in a radius of %2s", count, r);
			p.sendMessage(msg);
		} else {
			EntityType et = null;
			try {et = EntityType.valueOf(type);}
			catch(Throwable ex) {
				String error = Util.format(prefix + "Invalid Entity '%1s'!", type);
				p.sendMessage(error);
				return;
			}
			
			if(INVALID.contains(et)) {
				String error = Util.format(prefix + "Invalid Entity '%1s'!", type);
				p.sendMessage(error);
			} else {
				int count = killall(p, et, r);
				String msg = Util.format(prefix + "Killed %1s of '%2s' in a radius of %3s", count, et.name(), r);
				p.sendMessage(msg);
			}
		}
	}
	
	public static int killall(Player p, EntityType et, int r) {
		World w = p.getWorld();
		List<Entity> list = Util.newList();
		if(r == -1) {list = w.getEntities();}
		else {list = p.getNearbyEntities(r, r, r);}
		
		int count = 0;
		for(Entity e : list) {
			EntityType type= e.getType();
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
	
	public static int killall(Player p, int r) {
		World w = p.getWorld();
		List<Entity> list = Util.newList();
		if(r == -1) {list = w.getEntities();}
		else {list = p.getNearbyEntities(r, r, r);}
		
		int count = 0;
		for(Entity e : list) {
			EntityType type= e.getType();
			if(!INVALID.contains(type)) {
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