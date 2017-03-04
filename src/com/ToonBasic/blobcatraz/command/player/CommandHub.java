package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandHub extends ICommand {
	public CommandHub() {super("hub", "", "blobcatraz.player.hub", "lobby");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		World w = Bukkit.getWorld("Hub");
		double x = 66.50D;
		double y = 105.0D;
		double z = -23.50D;
		float yaw = -90.0F;
		float pitch = 0.0F;
		Location hub = new Location(w, x, y, z, yaw, pitch);
		p.teleport(hub);
	}
}