package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandTop extends ICommand {
	public CommandTop() {super("top", "", "blobcatraz.staff.top");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		World w = p.getWorld();
		Location l = p.getLocation();
		Block b = w.getHighestBlockAt(l);
		
		double x = b.getX() + 0.5D;
		double y = b.getY() + 1.0D;
		double z = b.getZ() + 0.5D;
		float pi = l.getPitch();
		float ya = l.getYaw();
		Location n = new Location(w, x, y, z, ya, pi);
		String msg = prefix + "You are now on top of the world!";
		p.sendMessage(msg);
		p.teleport(n);
	}
}