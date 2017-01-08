package com.toonbasic.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class FlyCMD extends Manager {

	String prefix = "§3{§bBlobcatraz§3} §f";

	public FlyCMD() {
		super("fly", "blobcatraz.fly", false);
	}
	public final void execute(final CommandSender sender, final String[] args) {
		Player p = (Player) sender;
		Player target = Bukkit.getServer().getPlayer(args[0]);
		p.setFlying(true);
		p.sendMessage(prefix + "Fly has been enabled!");
		{
			if(p.isFlying() == true) {
				p.setFlying(false);
			}
		}

		if(args.length == 0) {
			if(target == null) {
				p.sendMessage(prefix + "That player isn't online!");
			}
			if(target.isFlying() == false) {
				target.setFlying(true);
				target.sendMessage(prefix + "Fly has been enabled!");
				p.sendMessage(prefix + "Fly has been enabled for " + sender.getName());
			} else {
				if(target.isFlying() == true) {
					target.setFlying(false);
					target.sendMessage(prefix + "Fly has been disabled!");
					p.sendMessage(prefix + "Fly has been disabled for " + sender.getName());
				}
			}
		}
	}
}