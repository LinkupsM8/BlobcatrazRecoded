package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.ServerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandLag extends ICommand {
	public CommandLag() {super("lag", "[unloadchunks]", "blobcatraz.staff.lagg", "tps", "lagg", "ram", "cpu");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length == 0) {
			String[] msg = new String[] {
				prefix + "Server Information:",
				Util.color("&6&lTPS: &e" + ServerUtil.latestTicksPerSecond() + "/s"),
				Util.color("&6&lCPU Usage: &e" + ServerUtil.processorUsage() + "%"),
				Util.color("&6&lRAM Usage: &e" + ServerUtil.memoryUsage() + "MB")
			};
			p.sendMessage(msg);
		} else {
			String sub = args[0].toLowerCase();
			if(sub.equals("unloadchunks")) {
				List<World> worlds = Bukkit.getWorlds();
				for(World w : worlds) {
					Chunk[] chunks = w.getLoadedChunks();
					int amt = 0;
					for(Chunk c : chunks) {
						Entity[] entities = c.getEntities();
						int players = 0;
						for(Entity e : entities) {
							boolean b = (e instanceof Player);
							if(b) players++;
						}
						
						if(players == 0) {
							c.unload();
							amt++;
						}
					}
					String msg = Util.color("&7Unloaded &b" + amt + " chunks &7from &3" + w.getName());
					p.sendMessage(msg);
				}
			}
		}
	}
}