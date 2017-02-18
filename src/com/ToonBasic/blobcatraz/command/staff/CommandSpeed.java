package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandSpeed extends ICommand {

	public CommandSpeed() {
		super("speed", "<player> <speed/reset> [walk/fly]", "blobcatraz.staff.speed");
	}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
		Player p = (Player) cs;
		Player p2 = Bukkit.getServer().getPlayer(args[0]);
		
		if (p2 == null) {
			
			p.sendMessage("\u00a7cThat player does not exist or is not online!");
			return;
			
		} else if (!p.getUniqueId().equals(p2.getUniqueId()) && !p.hasPermission("blobcatraz.staff.speed.others")) {
			
			p.sendMessage("\u00a7cYou need permission \u00a74blobcatraz.staff.speed.others \u00a7cto edit other players' speed!");
			return;
			
		}
		
		if (args[1].equals("reset")) {
			
			p2.setWalkSpeed((float) 0.2);
			p2.setFlySpeed((float) 0.2);
			p2.sendMessage("\u00a7aYour speed has been reset!");
			
		} else {
		
			int i;
			
			try {
				
		        i = Integer.parseInt(args[1]);
		        
		        if (i < 0 || i > 100) {
		        	
		        	p.sendMessage("\u00a7cThe speed specified must be a whole number between 0 and 100!");
		        	return;
					
				}
				
		    } catch(Exception e) {
		    	
		    	p.sendMessage("\u00a7cThe speed specified must be a whole number between 0 and 100!");
		    	return;
		    	
		    }
			
			if (args.length == 2) {
			
				p2.setWalkSpeed((float) i / 100);
				p2.setFlySpeed((float) i / 100);
				p2.sendMessage("\u00a7aYour walking and flying speeds have been set to " + i + "!");
				
			} else if (args.length > 2) {
				
				if (args[2].equals("walk")) {
					
					p2.setWalkSpeed((float) i / 100);
					p2.sendMessage("\u00a7aYour walking speed have been set to " + i + "!");
					
				} else if (args[2].equals("fly")) {
					
					p2.setFlySpeed((float) i / 100);
					p2.sendMessage("\u00a7aYour flying speed have been set to " + i + "!");
					
				} else p.sendMessage("Missing Arguments: Did you mean /speed <player> <speed/reset> [walk/fly]");
				
			}
		
		}

	}

}
