package com.ToonBasic.blobcatraz.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class GameModeCMD extends Manager {

	String prefix = "�3{�bBlobcatraz�3} �f";

	public GameModeCMD() {
		super("gm", "blobcatraz.gamemode", false);
	}
	public final void execute(final CommandSender sender, final String[] args) {
		Player p = (Player) sender;
		p.sendMessage(prefix + "Missing arguments! Did you mean /gm [0/1]?");

		if(args.length == 0) {
			if(args[0].equalsIgnoreCase("1")) {
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(prefix + "Your GameMode has been updated to CREATIVE!");
			}
			if(args[0].equalsIgnoreCase("0")) {
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(prefix + "Your GameMode has been updated to SURVIVAL!");
			}
			if(args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) {
					p.sendMessage(prefix + "That player isn't online!");
				}
				if(args[0].equals("1")) {
					target.setGameMode(GameMode.CREATIVE);
					target.sendMessage(prefix + "Your GameMode has been updated to CREATIVE!");
					p.sendMessage(prefix + "You have updated your target's GameMode to CREATIVE!");
				} else
					if(args[0].equals("0")) {
						target.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(prefix + "You have updated your target's GameMode to SURVIVAL!");
					}
			}
		}
	}
}
