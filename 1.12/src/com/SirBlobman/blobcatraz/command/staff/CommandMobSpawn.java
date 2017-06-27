package com.SirBlobman.blobcatraz.command.staff;

import static org.bukkit.entity.EntityType.*;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandMobSpawn extends PlayerCommand implements TabCompleter {
	public static final List<EntityType> INVALID = Util.newList(
		PLAYER, DRAGON_FIREBALL, DROPPED_ITEM, LINGERING_POTION,
		LIGHTNING, SPLASH_POTION, COMPLEX_PART, AREA_EFFECT_CLOUD,
		EVOKER_FANGS, EXPERIENCE_ORB, THROWN_EXP_BOTTLE, ITEM_FRAME,
		ARMOR_STAND, SHULKER_BULLET, UNKNOWN
	);
	public CommandMobSpawn() {super("mobspawn", "<mob[,mob2,mob3,...]> [amount] [player]", "blobcatraz.staff.mobspawn", "spawnmob", "mob");}
	
	@Override
	public void run(Player p, String[] args) {
		String id = args[0];
		String target = null;
		int amount = 1;
		
		if(args.length > 1) {amount = NumberUtil.getInteger(args[1]);}
		if(amount < 1) amount = 1;
		if(amount > 50) amount = 50;
		if(args.length > 2) {target = args[2];}
		
		Location l = PlayerUtil.lookLocation(p);
		l.setY(l.getY() + 1.5D);
		if(target != null) {
			Player t = Bukkit.getPlayer(target);
			if(t != null) l = t.getLocation();
			else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		}
		
		int count = spawn(id, l, amount);
		String msg = Util.format(prefix + "You spawned &c%1s &fentities!", count);
		p.sendMessage(msg);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command cmd, String label, String[] args) {		
		if(args.length == 1) {
			List<String> list = Util.newList();
			EntityType[] tt = EntityType.values();
			for(EntityType et : tt) {
				if(!INVALID.contains(et)) {
					String name = et.name();
					list.add(name);
				} else continue;
			}
			
			List<String> match = Util.matching(list, args[0]);
			return match;
		} else return null;
	}
	
	private int spawn(String id, Location l, int amount) {
		String[] ss = id.split(",");
		List<EntityType> ee = mobs(ss);
		World w = l.getWorld();
		int count = 0;
		for(int i = 0; i < amount; i++) {
			Entity p = null;
			for(EntityType et : ee) {
				Entity e = w.spawnEntity(l, et);
				if(p == null) p = e;
				else {
					p.addPassenger(e);
					p = e;
				}
				count++;
			}
		}
		return count;
	}
	
	private List<EntityType> mobs(String... ss) {
		List<EntityType> list = Util.newList();
		for(String s : ss) {
			String u = s.toUpperCase();
			EntityType et = null;
			try {et = EntityType.valueOf(u);}
			catch(Throwable ex) {et = null;}
			
			boolean n = ((et == null) || (INVALID.contains(et)));
			if(!n) list.add(et);
		}
		
		if(list.isEmpty()) list.add(SLIME);
		return list;
	}
}