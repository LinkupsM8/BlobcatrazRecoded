package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSmite extends ICommand {
    public CommandSmite() {super("smite", "[player]", "blobcatraz.staff.smite", "lightning", "zap", "thor");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
    	Player p = (Player) cs;
        if(args.length > 0) {
        	String target = args[0];
        	Player t = Bukkit.getPlayer(target);
        	if(t != null) {
        		World w = p.getWorld();
        		Location l = p.getLocation();
        		w.strikeLightning(l);
        	} else {
        		String error = Util.color(Language.INVALID_TARGET);
        		p.sendMessage(error);
        	}
        } else {
        	Location l = PlayerUtil.lookLocation(p);
        	World w = p.getWorld();
        	w.strikeLightning(l);
        }
    }
}
