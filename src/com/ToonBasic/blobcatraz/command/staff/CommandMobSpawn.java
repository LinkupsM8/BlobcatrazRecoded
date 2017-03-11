package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandMobSpawn extends ICommand {
    public CommandMobSpawn() {
        super("spawnmob", "<mob> [amount] ", "blobcatraz.staff.spawnmob", "mob");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
        	int amount = 1;
        	if(args.length > 1) {
        		try{amount = Integer.parseInt(args[1]);} 
        		catch(Exception ex) {}
        	}
        	Player p = (Player) cs;
        	Location look = PlayerUtil.lookLocation(p);
        	look.setY(look.getY() + 1.5);
        	String ent = args[0].toUpperCase();
    		if(amount > 50) amount = 50;
    		String[] mobs = ent.split(",");
    		for(String mob : mobs) {
            	if(spawn(look, mob, amount)) {
            		String msg = Util.color(prefix + "&ESpawned &4" + amount+ " &eof " + mob);
            		p.sendMessage(msg);
            	} else {
            		String msg = Util.color(prefix + "&cInvalid Entity: " + mob);
            		p.sendMessage(msg);
            	}
    		}
        }
    }
    
    private boolean spawn(Location loc, String mob, int amount) {
    	try {
    		EntityType et = EntityType.valueOf(mob);
        	if(et != null) {
        		World w = loc.getWorld();
        		for(int i = amount; i > 0; i--) {
            		w.spawnEntity(loc, et);
        		}
        		return true;
        	} else return false;
    	} catch(Exception ex) {return false;}
    }
}
