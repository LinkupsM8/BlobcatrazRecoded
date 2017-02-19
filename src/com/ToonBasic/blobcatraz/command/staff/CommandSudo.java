package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandSudo extends ICommand {

	public CommandSudo() {
		super("sudo", "<player> <message/command>", "blobcatraz.staff.sudo");
	}

	@Override
	public void handleCommand(CommandSender sender, String[] args) {
		
		Player p = (Player) sender;

		if (args.length > 1) {
			
			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			
			if (p2 == null) {
				
				p.sendMessage("\u00a7cThat player does not exist or is not online!");
				return;
				
			}
			
			StringBuilder str = new StringBuilder(args[1]);
			
			if (args.length != 2) {
				
				str.append(" ");
				for (int i = 2; i < args.length - 1; i++) {
					
					str.append(args[i] + " ");
					
				}
				str.append(args[args.length - 1]);
			
			}
			
			String sudo = str.toString();
			
			if (sudo.startsWith("/")) {
				
				Bukkit.getServer().dispatchCommand(p2, sudo.substring(1));
				
			} else {
				
				p2.chat(sudo);
				
			}

		} else p.sendMessage("Missing Arguments: Did you mean /sudo <player> <message/command>");
	}

}
