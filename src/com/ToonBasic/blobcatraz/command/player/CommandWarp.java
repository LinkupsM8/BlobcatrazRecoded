package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigWarps;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandWarp extends ICommand {
	public CommandWarp() {super("warp", "[warp name]", "blobcatraz.player.warp", "warps");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length == 0) {
			StringBuilder build = new StringBuilder();
			List<String> list = ConfigWarps.stringWarps();
			for(int i = 0; i < list.size(); i++) {
				String warp = list.get(i);
				String perm2 = "blobcatraz.player.warps." + warp;
				if(p.hasPermission(perm2)) {
					if(i != 0) build.append(Util.color("&r, "));
					build.append(Util.color("&2" + warp));
				}
			}
			p.sendMessage(prefix + "List of warps:");
			p.sendMessage(build.toString());
		} else {
			String warp = Util.finalArgs(0, args);
			if(ConfigWarps.exists(warp)) {
				String perm2 = "blobcatraz.warps." + warp;
				if(p.hasPermission(perm2)) {
					Location l = ConfigWarps.getWarp(warp);
					p.teleport(l);
					p.sendMessage(prefix + Util.color("Warped to &c") + warp);
				} else {
					String error = "You don't have permission for that warp";
					p.sendMessage(error);
				}
			} else { 
				String error = prefix + "That warp does not exist!";
				p.sendMessage(error);
			}
		}
	}
}