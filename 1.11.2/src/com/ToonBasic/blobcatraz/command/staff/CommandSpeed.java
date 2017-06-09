package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSpeed extends ICommand {

	public CommandSpeed() {
		super("speed", "<player> <speed/reset> [walk/fly]", "blobcatraz.staff.speed");
	}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {	
		Player p = (Player) cs;
		Player p2 = Bukkit.getPlayer(args[0]);
		if (p2 == null) {
			String msg = prefix + Language.INVALID_TARGET;
			p.sendMessage(msg);
			return;
		} else if (!p.getUniqueId().equals(p2.getUniqueId()) && !p.hasPermission("blobcatraz.staff.speed.others")) {	
			p.sendMessage(Util.color("&cYou need permission &4blobcatraz.staff.speed.others &cto edit other players' speed!"));
			return;
		}
		
		if (args[1].toLowerCase().equals("reset")) {
			p2.setWalkSpeed(0.2F);
			p2.setFlySpeed(0.2F);
			p2.sendMessage(Util.color("&aYour speed has been reset!"));	
		} else {
			int i;
			try {			
		        i = Integer.parseInt(args[1]);	        
		        if (i < 0 || i > 100) {	        	
		        	p.sendMessage(Util.color("&cThe speed specified must be a whole number between 0 and 100!"));
		        	return;		
				}				
		    } catch(Exception e) {	
		    	p.sendMessage(Util.color("&cThe speed specified must be a whole number between 0 and 100!"));
		    	return;
		    }
			
			if (args.length == 2) {
				p2.setWalkSpeed((float) i / 100);
				p2.setFlySpeed((float) i / 100);
				p2.sendMessage(Util.color("&aYour walking and flying speeds have been set to " + i + "!"));			
			} else if (args.length > 2) {	
				if (args[2].toLowerCase().equals("walk")) {			
					p2.setWalkSpeed((float) i / 100);
					p2.sendMessage(Util.color("&aYour walking speed have been set to " + i + "!"));
				} else if (args[2].toLowerCase().equals("fly")) {	
					p2.setFlySpeed((float) i / 100);
					p2.sendMessage(Util.color("&aYour flying speed have been set to " + i + "!"));
				} else p.sendMessage(Util.color("Missing Arguments: Did you mean /speed <player> <speed/reset> [walk/fly]"));
			}
		}
	}
}