package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSmite extends PlayerCommand {
	public CommandSmite() {super("smite", "[player]", "blobcatraz.staff.smite", "lightning", "zap", "thor");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				World w = t.getWorld();
				Location l = t.getLocation();
				w.strikeLightning(l);
			} else {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} else {
			Location l = PlayerUtil.lookLocation(p);
			World w = p.getWorld();
			w.strikeLightning(l);
		}
	}
}