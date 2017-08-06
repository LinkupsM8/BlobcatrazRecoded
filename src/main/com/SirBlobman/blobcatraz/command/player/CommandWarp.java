package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandWarp extends PlayerCommand {
	public CommandWarp() {super("warp", "[name]", "blobcatraz.player.warp");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length == 0) {
			StringBuilder sb = new StringBuilder(prefix + "List of warps:\n");
			List<String> list = ConfigWarps.sWarps();
			for(int i = 0; i < list.size(); i++) {
				String name = list.get(i);
				String perm = "blobcatraz.warps." + name;
				if(p.hasPermission(perm)) {
					String p1 = Util.color("&r, ");
					String p2 = Util.format("&2%1s", name);
					if(i != 0) sb.append(p1);
					sb.append(p2);
				}
			}
			
			String msg = sb.toString();
			p.sendMessage(msg);
		} else {
			String name = Util.finalArgs(0, args);
			if(ConfigWarps.exists(name)) {
				String perm = "blobcatraz.warps." + name;
				if(p.hasPermission(perm)) {
					Location l = ConfigWarps.warp(name);
					p.teleport(l);
					String msg = Util.format(prefix + "Warped to &c%1s", name);
					p.sendMessage(msg);
				} else {
					String error = Util.format(prefix + Language.NO_PERMISSION, perm);
					p.sendMessage(error);
				}
			} else {
				String error = Util.format("The warp '%1s' does not exist!", name);
				p.sendMessage(error);
			}
		}
	}
}