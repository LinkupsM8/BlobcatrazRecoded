package com.ToonBasic.blobcatraz.command.special;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandWorld extends ICommand {
	public CommandWorld() {super("world", "[world name]", "blobcatraz.special.world");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String world = Util.finalArgs(0, args);
			world = world.toLowerCase();
			List<String> worlds = lowercaseWorlds();
			if(worlds.contains(world)) {
				World w = Bukkit.getWorld(world);
				Location s = w.getSpawnLocation();
				p.teleport(s);
				p.sendMessage(prefix + "You were teleported to the world: " + w.getName());
			} else {
				String error = prefix + "That world does not exist!";
				p.sendMessage(error);
			}
		} else {
			String worlds = Util.formatList('-', worlds());
			p.sendMessage(prefix + "Current Worlds:");
			p.sendMessage(worlds);
		}
	}
	
	private List<String> worlds() {
		List<String> list = Util.newList();
		List<World> worlds = Bukkit.getWorlds();
		for(World w : worlds) {
			String name = w.getName();
			list.add(name);
		}
		Collections.sort(list);
		return list;
	}
	
	private List<String> lowercaseWorlds() {
		List<String> worlds = worlds();
		List<String> list = Util.newList();
		for(String s : worlds) {
			String l = s.toLowerCase();
			list.add(l);
		}
		Collections.sort(list);
		return list;
	}
}