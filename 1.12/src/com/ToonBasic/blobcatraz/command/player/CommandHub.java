package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandHub extends ICommand {
	public CommandHub() {super("hub", "", "blobcatraz.player.hub", "lobby", "spawn");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		hub(p);
	}
	
	public static void hub(Player p) {
		Location hub = hub();
		p.teleport(hub);
	}
	
	public static Location hub() {
		World w = Bukkit.getWorld("Hub");
		if(w == null) {
			List<World> worlds = Bukkit.getWorlds();
			World w2 = worlds.get(0);
			Location hub = w2.getSpawnLocation();
			return hub;
		} else {
			double x = 66.5D;
			double y = 105.0D;
			double z = -23.5D;
			float yaw = 90.0F;
			float pitch = 0.0F;
			Location hub = new Location(w, x, y, z, yaw, pitch);
			return hub;
		}
	}
}