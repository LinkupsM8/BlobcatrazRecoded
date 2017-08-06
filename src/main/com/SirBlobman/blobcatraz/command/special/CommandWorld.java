package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandWorld extends PlayerCommand {
	public CommandWorld() {super("world", "[name]", "blobcatraz.special.world", "tpworld");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			String l = name.toLowerCase();
			List<String> list = lowercase();
			if(list.contains(l)) {
				World w = Bukkit.getWorld(l);
				Location s = w.getSpawnLocation();
				p.teleport(s);
				String msg = Util.format(prefix + "You teleported to a world called &2%1s", w.getName());
				p.sendMessage(msg);
			} else {
				String error = prefix + "That world does not exist!";
				p.sendMessage(error);
			}
		} else {
			List<String> list = worlds();
			String j = Util.joinList(list, "\n-", 0);
			String msg = prefix + "Current Worlds: " + j;
			p.sendMessage(msg);
		}
	}
	
	private List<String> worlds() {
		List<String> list = Util.newList();
		List<World> ww = Bukkit.getWorlds();
		for(World w : ww) {
			String name = w.getName();
			list.add(name);
		}
		
		Collections.sort(list);
		return list;
	}
	
	private List<String> lowercase() {
		List<String> list = Util.newList();
		List<String> ss = worlds();
		for(String s : ss) {
			String l = s.toLowerCase();
			list.add(l);
		}
		
		Collections.sort(list);
		return list;
	}
}