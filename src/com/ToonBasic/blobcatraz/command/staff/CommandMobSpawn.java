package com.ToonBasic.blobcatraz.command.staff;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;
import com.google.common.collect.Sets;

@PlayerOnly
public class CommandMobSpawn extends ICommand {
    public CommandMobSpawn() {
        super("spawnmob", "<mob>", "blobcatraz.staff.spawnmob", "mob");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
        	Player p = (Player) cs;
        	Set<Material> air = Sets.newHashSet();
        	Block look = p.getTargetBlock(air, 100);
        	Location loc = look.getLocation();
        	String mob = args[0].toUpperCase();
        	if(spawn(loc, mob)) {
        		String msg = Util.color(prefix + "&eEntity Spawned!");
        		p.sendMessage(msg);
        	} else {
        		String msg = Util.color(prefix + "&cInvalid Entity!");
        		p.sendMessage(msg);
        	}
        }
    }
    
    private boolean spawn(Location loc, String mob) {
    	EntityType et = EntityType.valueOf(mob);
    	if(et != null) {
    		World w = loc.getWorld();
    		w.spawnEntity(loc, et);
    		return true;
    	} else return false;
    }
}
