package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandCenter extends ICommand {
	public CommandCenter() {super("center", "", "blobcatraz.special.center", "middle", ".5");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Location l = p.getLocation();
		World w = l.getWorld();
		double x = l.getBlockX() + 0.5D;
		double y = l.getBlockY() + 0.5D;
		double z = l.getBlockZ() + 0.5D;
		float yaw = l.getYaw();
		float pit = l.getPitch();
		Location n = new Location(w, x, y, z, yaw, pit);
		p.teleport(n);
		p.sendMessage(prefix + "You are now at the center of this block");
	}
}