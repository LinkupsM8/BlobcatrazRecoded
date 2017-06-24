package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.ServerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandLag extends ICommand {
	public CommandLag() {super("lag", "<info/unloadchunks>", "blobcatraz.staff.lagg", "tps", "lagg");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("info")) {
			double tpsD = ServerUtil.latestTPS();
			double cpuD = ServerUtil.cpuUsage();
			long framL = ServerUtil.freeRAM();
			long uramL = ServerUtil.usedRAM();
			long tramL = ServerUtil.totalRAM();
			
			String tps = NumberUtil.cropDecimal(tpsD, 2) + "/s";
			String cpu = NumberUtil.cropDecimal(cpuD, 5) + "%";
			String fram = framL + "MB";
			String uram = uramL + "MB";
			String tram = tramL + "MB";
			String[] msg = Util.color(
				prefix + "Server Information:",
				"&6&lTPS: &e" + tps,
				"&6&lCPU Usage: &e" + cpu,
				"&6&lFree RAM: &e" + fram,
				"&6&lUsed RAM: &e" + uram,
				"&6&lTotal RAM: &e" + tram
			);
			cs.sendMessage(msg);
		} else if(sub.equals("unloadchunks")) {
			List<World> ww = Bukkit.getWorlds();
			for(World w : ww) {
				Chunk[] cc = w.getLoadedChunks();
				int amount = 0;
				for(Chunk c : cc) {
					Entity[] ee = c.getEntities();
					int players = 0;
					for(Entity e : ee) {
						boolean b = (e instanceof Player);
						if(b) players++;
					}
					
					if(players == 0) {
						c.unload();
						amount++;
					}
				}
				
				String name = w.getName();
				String msg = Util.format("&7Unloaded &b%1s &7chunks from &e%2s", amount, name);
				cs.sendMessage(msg);
			}
		} else {
			String error = prefix + getFormattedUsage(getCommandUsed());
			cs.sendMessage(error);
		}
	}
}