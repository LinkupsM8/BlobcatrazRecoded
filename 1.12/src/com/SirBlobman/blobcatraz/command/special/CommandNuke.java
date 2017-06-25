package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class CommandNuke extends ICommand {
	public CommandNuke() {super("nuke", "<player>", "blobcatraz.special.nuke", "tntrain", "antioch");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			Location l = t.getLocation();
			World w = l.getWorld();
			for(int x1 = -10; x1 <= 10; x1 += 5) {
				for(int z1 = -10; z1 <= 10; z1 += 5) {
					int x = l.getBlockX() + x1;
					int y = (int) Math.min(l.getY() + 100, 255);
					int z = l.getBlockZ() + z1;
					Location n = new Location(w, x, y, z);
					TNTPrimed tnt = w.spawn(n, TNTPrimed.class);
					tnt.setFuseTicks(100);
				}
			}
		} else {
			String msg = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(msg);
		}
	}
}