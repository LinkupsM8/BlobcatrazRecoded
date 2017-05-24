package com.ToonBasic.blobcatraz.command.staff;

import static org.bukkit.entity.EntityType.*;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandMobSpawn extends ICommand {
    public CommandMobSpawn() {super("spawnmob", "<mob[,mob2,mob3,...]> [amount] [player]", "blobcatraz.staff.spawnmob", "mob", "mobspawn");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        String id = args[0];
        String target = null;
        int amount = 1;
        if(args.length > 1) amount = NumberUtil.getInteger(args[0]);
        if(amount > 1) amount = 1;
        if(amount > 50) amount = 50;
        if(args.length > 2) {target = args[2];}
        
        Location l = PlayerUtil.lookLocation(p);
        l.setY(l.getY() + 1.5D);
        if(target != null) {
        	Player t = Bukkit.getPlayer(target);
        	if(t != null) l = t.getLocation();
        	else {p.sendMessage(prefix + Language.INVALID_TARGET);}
        }
        
        int count = spawn(id, l, amount);
        String msg = prefix + Util.color("You spawned &c" + count + " &fentities!");
        p.sendMessage(msg);
    }
    
    private int spawn(String id, Location l, int amount) {
    	String[] split = id.split(",");
    	List<EntityType> ets = mobs(split);
    	World w = l.getWorld();
    	int count = 0;
    	for(int i = 0; i < amount; i++) {
        	Entity prev = null;
        	for(EntityType et : ets) {
        		Entity e = w.spawnEntity(l, et);
        		if(prev == null) {prev = e;}
        		else {
        			prev.addPassenger(e);
        			prev = e;
        		}
        		count++;
        	}
    	}
    	return count;
    }
    
    public static final List<EntityType> INVALID = Util.newList(
		PLAYER,
		DRAGON_FIREBALL,
		DROPPED_ITEM,
		LINGERING_POTION,
		LIGHTNING,
		SPLASH_POTION,
		COMPLEX_PART, 
		AREA_EFFECT_CLOUD, 
		EVOKER_FANGS, 
		EXPERIENCE_ORB, 
		THROWN_EXP_BOTTLE, 
		SHULKER_BULLET, 
		UNKNOWN
    );
    
    private List<EntityType> mobs(String[] ss) {
    	List<EntityType> list = Util.newList();
    	for(String s : ss) {
    		s = s.toUpperCase();
    		EntityType et = null;
    		try {et = EntityType.valueOf(s);}
    		catch(Exception ex) {et = null;}
    		boolean n = ((et == null) || (INVALID.contains(et)));
    		if(!n) list.add(et);
    	}
    	if(list.isEmpty()) list.add(SLIME);
    	return list;
    }
}