package com.ToonBasic.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HealCMD extends Manager {

	String prefix = "�3{�bBlobcatraz�3} �f";

	public HealCMD() {
		super("fly", "blobcatraz.heal", false);
	}
	public final void execute(final CommandSender sender, final String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			p.setHealth(20);
			p.setFoodLevel(20);
			p.sendMessage(prefix + "You have been healed!");
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if(target == null) {
			p.sendMessage(prefix + "That player isn't online!");
		}
		target.setFoodLevel(20);
		target.setHealth(20);
		target.sendMessage(prefix + "You have been healed!");
		p.sendMessage(prefix + "You have healed " + target.getName() + "!");
	}
}
